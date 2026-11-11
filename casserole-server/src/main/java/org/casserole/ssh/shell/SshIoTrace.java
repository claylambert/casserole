/*
 * Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
 * All rights reserved.
 *
 * Author: Clay Lambert
 *
 * Licensed under the MIT License.
 * If you use or modify this source, you must include this copyright notice
 * and the MIT License permission notice in all copies or substantial portions
 * of the software, including modifications.
 */

package org.casserole.ssh.shell;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.logging.log4j.Logger;
import org.apache.sshd.common.AttributeRepository;
import org.apache.sshd.common.AttributeStore;
import org.apache.sshd.common.channel.Channel;
import org.apache.sshd.common.channel.ChannelListener;
import org.apache.sshd.common.io.IoSession;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.session.SessionListener;
import org.apache.sshd.server.forward.TcpipServerChannel;
import org.casserole.log.CasseroleLog;

/**
 * The {@code SshIoTrace} class writes an info line for every connect and disconnect on the Casserole SSH listener.
 * One line starts with {@code ssh-io}. The password never appears.
 *
 * {@code iface-mac} is the hardware address of the server interface that owns the local socket. {@code peer-mac} stays {@code absent}. The SSH session does not carry the peer hardware address.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-11-01)
 */
class SshIoTrace {

    private static final Logger LOG = CasseroleLog.get(SshIoTrace.class);

    private static final AttributeRepository.AttributeKey<Long> SESSION_OPENED_MS =
            new AttributeRepository.AttributeKey<Long>();

    private static final AttributeRepository.AttributeKey<Long> CHANNEL_OPENED_MS =
            new AttributeRepository.AttributeKey<Long>();

    private static final AttributeRepository.AttributeKey<String> LAST_DISCONNECT =
            new AttributeRepository.AttributeKey<String>();

    private static final int TEXT_CAP = 160;

    /**
     * The constructor is private so callers use the static methods.
     * The class holds no instance state.
     */
    private SshIoTrace() {
    }

    /**
     * The method attaches the session listener and the channel listener before {@code start}.
     * Each listener writes one {@code ssh-io} line for the events it sees.
     *
     * @param server The server is the listener about to start.
     */
    static void attach(org.apache.sshd.server.SshServer server) {
        /**
         * The session listener records accept, open, authentication, exception, disconnect, and close.
         * A disconnect stores its tail so the later close line can repeat it.
         */
        server.addSessionListener(new SessionListener() {
            // @aa-sdd PK-0506 docs/specs/java/org/casserole/casserole.spec.md
            /**
             * The method writes a {@code session-accept} line.
             *
             * @param session The session is the session that was established.
             */
            @Override
            public void sessionEstablished(Session session) {
                info("session-accept", session, null, null);
            }

            /**
             * The method stores the open time and writes a {@code session-open} line.
             *
             * @param session The session is the session that was created.
             */
            @Override
            public void sessionCreated(Session session) {
                markOpened(session, SESSION_OPENED_MS);
                info("session-open", session, null, null);
            }

            /**
             * The method writes an {@code authenticated} line when the event is authentication.
             * Any other event is ignored.
             *
             * @param session The session is the session that reported the event.
             * @param event The event is the session event.
             */
            @Override
            public void sessionEvent(Session session, Event event) {
                if (event == Event.Authenticated) {
                    info("authenticated", session, null, null);
                }
            }

            /**
             * The method writes a {@code session-exception} line with the exception tail.
             *
             * @param session The session is the session that failed.
             * @param caught The throwable is the failure.
             */
            @Override
            public void sessionException(Session session, Throwable caught) {
                info("session-exception", session, null, exceptionTail(caught));
            }

            /**
             * The method writes a {@code session-disconnect} line and stores that tail for the close line.
             * A store failure logs {@code trace-fault} and still writes the disconnect line. The initiator word is {@code local} or {@code peer}.
             *
             * @param session The session is the session that disconnected.
             * @param reason The reason is the numeric disconnect reason.
             * @param message The message is the disconnect text.
             * @param language The language is the disconnect language tag.
             * @param initiator The flag is true when this side started the disconnect.
             */
            @Override
            public void sessionDisconnect(
                    Session session, int reason, String message, String language, boolean initiator) {
                String tail = "reason=" + Integer.toString(reason)
                        + " reason-text=" + token(message)
                        + " language=" + token(language)
                        + " initiator=" + (initiator ? "local" : "peer");
                try {
                    session.setAttribute(LAST_DISCONNECT, tail);
                } catch (RuntimeException ex) {
                    LOG.info("ssh-io event=trace-fault exception=" + ex.getClass().getSimpleName());
                }
                info("session-disconnect", session, null, tail);
            }

            /**
             * The method writes a {@code session-closed} line with the duration and any stored disconnect tail.
             * A missing tail is {@code disconnect=absent}.
             *
             * @param session The session is the session that closed.
             */
            @Override
            public void sessionClosed(Session session) {
                String prior = "disconnect=absent";
                try {
                    String stored = session.getAttribute(LAST_DISCONNECT);
                    if (stored != null && stored.length() > 0) {
                        prior = stored;
                    }
                } catch (RuntimeException ex) {
                    prior = "disconnect=absent";
                }
                String tail = "duration-ms=" + duration(session, SESSION_OPENED_MS) + " " + prior;
                info("session-closed", session, null, tail);
            }
        });
        /**
         * The channel listener records open, open failure, and close.
         * Initialization stores the open time used by the close line.
         */
        server.addChannelListener(new ChannelListener() {
            // @aa-sdd PK-0507 docs/specs/java/org/casserole/casserole.spec.md
            /**
             * The method stores the channel open time.
             *
             * @param channel The channel is the channel that was initialized.
             */
            @Override
            public void channelInitialized(Channel channel) {
                markOpened(channel, CHANNEL_OPENED_MS);
            }

            /**
             * The method writes a {@code channel-open} line.
             *
             * @param channel The channel is the channel that opened.
             */
            @Override
            public void channelOpenSuccess(Channel channel) {
                info("channel-open", channel.getSession(), channel, null);
            }

            /**
             * The method writes a {@code channel-open-failed} line with the exception tail.
             *
             * @param channel The channel is the channel that failed to open.
             * @param reason The reason is the failure.
             */
            @Override
            public void channelOpenFailure(Channel channel, Throwable reason) {
                info("channel-open-failed", channel.getSession(), channel, exceptionTail(reason));
            }

            /**
             * The method writes a {@code channel-closed} line with the duration.
             * A non-null reason appends the exception tail.
             *
             * @param channel The channel is the channel that closed.
             * @param reason The reason is the close failure, or null when the close was clean.
             */
            @Override
            public void channelClosed(Channel channel, Throwable reason) {
                String tail = "duration-ms=" + duration(channel, CHANNEL_OPENED_MS);
                if (reason != null) {
                    tail = tail + " " + exceptionTail(reason);
                }
                info("channel-closed", channel.getSession(), channel, tail);
            }
        });
    }

    /**
     * The method writes an {@code auth-reject} line when an authenticator returns false.
     * The password is not a parameter of this method.
     *
     * @param session The session is the session that offered the name.
     * @param username The username is the offered name.
     */
    static void authReject(Session session, String username) {
        info("auth-reject", session, null, null, username);
    }

    /**
     * The method writes one info line with no username override.
     *
     * @param event The event is the event name.
     * @param session The session is the SSH session, or null.
     * @param channel The channel is the SSH channel, or null.
     * @param extra The extra text is appended when it is not empty.
     */
    private static void info(String event, Session session, Channel channel, String extra) {
        info(event, session, channel, extra, null);
    }

    /**
     * The method writes one {@code ssh-io} info line.
     * A failure while building the line logs {@code trace-fault} and the exception class name. The password is not written.
     *
     * @param event The event is the event name.
     * @param session The session is the SSH session, or null.
     * @param channel The channel is the SSH channel, or null when the line has no channel fields.
     * @param extra The extra text is appended when it is not empty.
     * @param userOverride The override replaces the session user when it is not null.
     */
    private static void info(
            String event, Session session, Channel channel, String extra, String userOverride) {
        try {
            StringBuilder line = new StringBuilder();
            line.append("ssh-io");
            line.append(" event=").append(token(event));
            appendCommon(line, session, userOverride);
            if (channel != null) {
                appendChannel(line, channel);
            }
            if (extra != null && extra.length() > 0) {
                line.append(' ').append(extra);
            }
            LOG.info(line.toString());
        } catch (RuntimeException ex) {
            LOG.info("ssh-io event=trace-fault exception=" + ex.getClass().getSimpleName());
        }
    }

    /**
     * The method appends the session id, the user, both endpoints, both versions, and both hardware-address fields.
     * A read failure for an address or a version leaves that field absent. {@code peer-mac} is always {@code absent}.
     *
     * @param line The line is the buffer being built.
     * @param session The session is the SSH session, or null.
     * @param userOverride The override replaces the session user when it is not null.
     */
    private static void appendCommon(StringBuilder line, Session session, String userOverride) {
        line.append(" session=").append(sessionId(session));
        if (userOverride != null) {
            line.append(" user=").append(token(userOverride));
        } else {
            line.append(" user=").append(userOf(session));
        }
        SocketAddress remote = null;
        SocketAddress local = null;
        String client = null;
        String server = null;
        if (session != null) {
            try {
                remote = session.getRemoteAddress();
            } catch (RuntimeException ex) {
                remote = null;
            }
            try {
                local = session.getLocalAddress();
            } catch (RuntimeException ex) {
                local = null;
            }
            try {
                client = session.getClientVersion();
            } catch (RuntimeException ex) {
                client = null;
            }
            try {
                server = session.getServerVersion();
            } catch (RuntimeException ex) {
                server = null;
            }
        }
        appendEndpoint(line, "remote", remote);
        appendEndpoint(line, "local", local);
        line.append(" client-version=").append(token(client));
        line.append(" server-version=").append(token(server));
        line.append(" iface-mac=").append(ifaceMac(local));
        line.append(" peer-mac=absent");
    }

    /**
     * The method appends the channel id, the recipient, the channel type, and the channel class.
     * A TCP/IP server channel uses its TCP/IP type name. Any other channel uses {@code absent} for the type. A missing id becomes {@code -1}.
     *
     * @param line The line is the buffer being built.
     * @param channel The channel is the SSH channel.
     */
    private static void appendChannel(StringBuilder line, Channel channel) {
        long id = -1L;
        long recipient = -1L;
        try {
            id = channel.getChannelId();
        } catch (RuntimeException ex) {
            id = -1L;
        }
        try {
            recipient = channel.getRecipient();
        } catch (RuntimeException ex) {
            recipient = -1L;
        }
        line.append(" channel-id=").append(Long.toString(id));
        line.append(" channel-recipient=").append(Long.toString(recipient));
        String typeName = "absent";
        if (channel instanceof TcpipServerChannel) {
            TcpipServerChannel tcp = (TcpipServerChannel) channel;
            if (tcp.getTcpipChannelType() != null) {
                typeName = token(tcp.getTcpipChannelType().name());
            }
        }
        line.append(" channel-type=").append(typeName);
        String className = "absent";
        if (channel.getClass() != null && channel.getClass().getSimpleName() != null) {
            className = token(channel.getClass().getSimpleName());
        }
        line.append(" channel-class=").append(className);
    }

    /**
     * The method appends the IP and the port for one endpoint.
     * An internet address uses the host address and the port. A null address, or an address that is not an internet address, uses {@code absent} for a missing part.
     *
     * @param line The line is the buffer being built.
     * @param prefix The prefix is {@code remote} or {@code local}.
     * @param address The address is the socket address, or null.
     */
    private static void appendEndpoint(StringBuilder line, String prefix, SocketAddress address) {
        String ip = "absent";
        String port = "absent";
        if (address instanceof InetSocketAddress) {
            InetSocketAddress inet = (InetSocketAddress) address;
            if (inet.getAddress() != null) {
                ip = token(inet.getAddress().getHostAddress());
            } else if (inet.getHostString() != null) {
                ip = token(inet.getHostString());
            }
            port = Integer.toString(inet.getPort());
        } else if (address != null) {
            ip = token(address.toString());
        }
        line.append(' ').append(prefix).append("-ip=").append(ip);
        line.append(' ').append(prefix).append("-port=").append(port);
    }

    /**
     * The method returns the input-output session id.
     * A null session, a missing input-output session, or a read failure returns {@code absent}.
     *
     * @param session The session is the SSH session, or null.
     * @return The return value is the id text, or {@code absent}.
     */
    private static String sessionId(Session session) {
        if (session == null) {
            return "absent";
        }
        try {
            IoSession io = session.getIoSession();
            if (io == null) {
                return "absent";
            }
            return Long.toString(io.getId());
        } catch (RuntimeException ex) {
            return "absent";
        }
    }

    /**
     * The method returns the session user name as a log token.
     * A null session or a read failure returns {@code absent}.
     *
     * @param session The session is the SSH session, or null.
     * @return The return value is the user token, or {@code absent}.
     */
    private static String userOf(Session session) {
        if (session == null) {
            return "absent";
        }
        try {
            return token(session.getUsername());
        } catch (RuntimeException ex) {
            return "absent";
        }
    }

    /**
     * The method returns the exception class and the exception text as one tail.
     * A null throwable returns {@code exception=absent exception-text=absent}.
     *
     * @param caught The throwable is the failure, or null.
     * @return The return value is the exception tail.
     */
    private static String exceptionTail(Throwable caught) {
        if (caught == null) {
            return "exception=absent exception-text=absent";
        }
        String name = caught.getClass().getSimpleName();
        if (name == null || name.length() == 0) {
            name = caught.getClass().getName();
        }
        return "exception=" + token(name) + " exception-text=" + token(caught.getMessage());
    }

    /**
     * The method stores the current time on the store when that key is still empty.
     * A null store returns. A failure logs {@code trace-fault}.
     *
     * @param store The store is the session or the channel, or null.
     * @param key The key is the attribute that holds the open time.
     */
    private static void markOpened(AttributeStore store, AttributeRepository.AttributeKey<Long> key) {
        if (store == null) {
            return;
        }
        try {
            if (store.getAttribute(key) == null) {
                store.setAttribute(key, Long.valueOf(System.currentTimeMillis()));
            }
        } catch (RuntimeException ex) {
            LOG.info("ssh-io event=trace-fault exception=" + ex.getClass().getSimpleName());
        }
    }

    /**
     * The method returns the milliseconds since the stored open time.
     * A missing time, a null store, or a read failure returns {@code absent}. A negative delta becomes {@code 0}.
     *
     * @param store The store is the session or the channel, or null.
     * @param key The key is the attribute that holds the open time.
     * @return The return value is the duration text, or {@code absent}.
     */
    private static String duration(AttributeStore store, AttributeRepository.AttributeKey<Long> key) {
        if (store == null) {
            return "absent";
        }
        try {
            Long opened = store.getAttribute(key);
            if (opened == null) {
                return "absent";
            }
            long delta = System.currentTimeMillis() - opened.longValue();
            if (delta < 0L) {
                delta = 0L;
            }
            return Long.toString(delta);
        } catch (RuntimeException ex) {
            return "absent";
        }
    }

    /**
     * The method returns the hardware address of the interface that owns the local address.
     * A non-internet address, a wildcard address, a missing interface, an empty hardware address, or a socket failure returns {@code absent}.
     *
     * @param local The local address is the listener socket address.
     * @return The return value is the hardware address text, or {@code absent}.
     */
    private static String ifaceMac(SocketAddress local) {
        if (!(local instanceof InetSocketAddress)) {
            return "absent";
        }
        InetAddress address = ((InetSocketAddress) local).getAddress();
        if (address == null || address.isAnyLocalAddress()) {
            return "absent";
        }
        try {
            NetworkInterface nic = NetworkInterface.getByInetAddress(address);
            if (nic == null) {
                nic = findInterface(address);
            }
            if (nic == null) {
                return "absent";
            }
            byte[] hardware = nic.getHardwareAddress();
            if (hardware == null || hardware.length == 0) {
                return "absent";
            }
            return formatMac(hardware);
        } catch (SocketException ex) {
            return "absent";
        }
    }

    /**
     * The method searches every network interface for the given address.
     * A missing interface list returns null. No match returns null.
     *
     * @param address The address is the local internet address.
     * @return The return value is the matching interface, or null.
     * @throws SocketException The method throws this exception when the interface list cannot be read.
     */
    private static NetworkInterface findInterface(InetAddress address) throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        if (interfaces == null) {
            return null;
        }
        while (interfaces.hasMoreElements()) {
            NetworkInterface candidate = interfaces.nextElement();
            Enumeration<InetAddress> addresses = candidate.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress one = addresses.nextElement();
                if (address.equals(one)) {
                    return candidate;
                }
            }
        }
        return null;
    }

    /**
     * The method formats a hardware address as lowercase hex bytes separated by colons.
     * A byte below {@code 16} gets a leading zero.
     *
     * @param hardware The bytes are the hardware address.
     * @return The return value is the formatted address.
     */
    private static String formatMac(byte[] hardware) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < hardware.length; i++) {
            if (i > 0) {
                out.append(':');
            }
            int value = hardware[i] & 0xff;
            if (value < 16) {
                out.append('0');
            }
            out.append(Integer.toHexString(value));
        }
        return out.toString();
    }

    /**
     * The method turns raw text into one log token.
     * A null or blank value returns {@code absent}. The token is cut at {@code 160} characters. A space, an equals sign, or delete becomes an underscore.
     *
     * @param raw The raw text is the value to place in a log field.
     * @return The return value is the token.
     */
    private static String token(String raw) {
        if (raw == null) {
            return "absent";
        }
        String trimmed = raw.trim();
        if (trimmed.length() == 0) {
            return "absent";
        }
        int limit = trimmed.length();
        if (limit > TEXT_CAP) {
            limit = TEXT_CAP;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            char c = trimmed.charAt(i);
            if (c <= ' ' || c == '=' || c == '\u007f') {
                out.append('_');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
/*****************************************************************************************
 * End of Class: SshIoTrace.java 110126
 ****************************************************************************************/
