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

package org.casserole.app.ui.ssh;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

// @aa-sdd PK-0119 docs/specs/java/org/aetheris/swing/swing.spec.md
/**
 * The {@code PasteEditorDialog} type is the modal editor for a clipboard paste or a file paste.
 * The dialog title is {@code Edit paste}.
 * The modality is application modal, so the rest of the application waits.
 * The control-and-Enter key accepts the text.
 * The OK button accepts the text.
 * The Enter key in the text area inserts a newline.
 * The multi-line blob stays editable because of that newline.
 * The terminal Enter, the Enter key on the console outside this dialog, still submits.
 * The Cancel button drops the text.
 * The dialog does not write to the terminal.
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
 * @author clayl (2026-08-25)
 */
public class PasteEditorDialog extends JDialog {

    private final JTextArea area;
    private boolean accepted;

    /**
     * The constructor builds the editor and does not show it.
     * The close operation disposes the dialog.
     * The content background is the panel background from the theme.
     * The hint says control and Enter, or OK, send the text to the terminal.
     * The hint says Enter inserts a newline.
     * The text area is {@code 18} rows by {@code 72} columns.
     * The null initial text becomes an empty string.
     * The line wrap is on.
     * The words stay whole at the wrap.
     * The caret starts at the first character.
     * The null font leaves the text-area font unchanged.
     * The text-area background is a darker panel background.
     * The text, the caret, and the buttons take their colors from the theme.
     * The tooltip on the text area says to edit the text before it is inserted, and that Enter adds a line.
     * The OK tooltip says OK inserts the text and that control and Enter do the same.
     * The Cancel tooltip says Cancel drops the text and that nothing is inserted.
     * The control-and-Enter key in the text area runs the same accept action as OK.
     * The dialog is packed and placed relative to the owner frame.
     *
     * @param owner The parent frame. The dialog is centered on this frame.
     * @param theme The dark theme that paints the dialog.
     * @param initialText The clipboard text or the file text. The value may be null.
     * @param editorFont The terminal typeface at the control-panel spinner size. The value may be null.
     */
    public PasteEditorDialog(JFrame owner, SshClientTheme theme, String initialText, Font editorFont) {
        super(owner, "Edit paste", Dialog.ModalityType.APPLICATION_MODAL);
        this.accepted = false;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(theme.getPanelBackground());
        setLayout(new BorderLayout(8, 8));

        JLabel hint = new JLabel("Ctrl+Enter or OK sends to the terminal. Enter inserts a newline.");
        theme.applyLabel(hint);
        hint.setBorder(new EmptyBorder(8, 8, 0, 8));
        add(hint, BorderLayout.NORTH);

        area = new JTextArea(18, 72);
        area.setText(initialText == null ? "" : initialText);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setCaretPosition(0);
        if (editorFont != null) {
            area.setFont(editorFont);
        }
        area.setBackground(theme.getPanelBackground().darker());
        area.setForeground(theme.getPanelForeground());
        area.setCaretColor(theme.getPanelForeground());
        area.setToolTipText("Edit the text before it is inserted. Enter adds a line.");
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(new EmptyBorder(8, 8, 8, 8));
        theme.applyScrollPane(scroll);
        add(scroll, BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        theme.applyButton(ok);
        theme.applyButton(cancel);
        ok.setToolTipText("OK inserts this text. Ctrl+Enter does the same.");
        cancel.setToolTipText("Cancel drops this text. Nothing is inserted.");
        /**
         * The listener accepts the text and closes the dialog.
         */
        ok.addActionListener(new ActionListener() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptAndClose();
            }
        });
        /**
         * The listener marks the text as not accepted.
         * The listener then disposes the dialog.
         */
        cancel.addActionListener(new ActionListener() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                accepted = false;
                dispose();
            }
        });
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        theme.applyPanel(buttons);
        buttons.add(ok);
        buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);

        area.getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK),
                "submit-paste");
        /**
         * The action accepts the text and closes the dialog.
         * The key for the action is {@code submit-paste}.
         */
        area.getActionMap().put("submit-paste", new AbstractAction() {
            // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
            // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
            // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
            // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
            // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
            // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
            // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
            // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
            // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
            // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptAndClose();
            }
        });

        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * The isAccepted method reports whether OK or control-Enter accepted the text.
     * The flag stays false after Cancel.
     * The flag stays false until one of those accept paths runs.
     *
     * @return The return value is true when the text was accepted.
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * The getEditedText method returns the text currently in the area.
     * The method returns that text after Cancel as well as after OK.
     *
     * @return The return value is the edited text.
     */
    public String getEditedText() {
        return area.getText();
    }

    /**
     * The acceptAndClose method marks the text as accepted.
     * The method then disposes the dialog.
     */
    private void acceptAndClose() {
        accepted = true;
        dispose();
    }
}
/*****************************************************************************************
 * End of Class: PasteEditorDialog.java 082526
 ****************************************************************************************/
