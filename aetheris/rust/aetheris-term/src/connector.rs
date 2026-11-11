// Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
// All rights reserved.
//
// Author: Clay Lambert
//
// Licensed under the MIT License.
// If you use or modify this source, you must include this copyright notice
// and the MIT License permission notice in all copies or substantial portions
// of the software, including modifications.

//! The program that owns the socket implements [`Connector`].
//!
//! The library asks that program to read, to write, and to resize. `read` returns the next chunk. A length of zero ends the session. `write` sends bytes the encoder already produced. `resize` reports the columns and the rows after the grid has changed. The core does not open the socket. The core does not call this trait itself.
//!
//! @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
//! @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
//! @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
//! @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
//! @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
//! @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
//! @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
//! @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
//! @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
//! @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
//! @author clayl (2026-10-23)

use std::io;

/// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/// The [`Connector`] trait is bytes in, bytes out, and a size report.
/// The `russh` crate stays behind the program's own implementation. This library does not open the socket.
pub trait Connector {
    /// The `read` method fills `buf` with the next chunk from the socket.
    /// The returned length is the number of bytes written into `buf`. A length of zero means the session ended. An `Err` is an I/O failure from the implementation.
    fn read(&mut self, buf: &mut [u8]) -> io::Result<usize>;

    /// The `write` method sends `buf` on the socket.
    /// The bytes are ones the encoder already produced. The returned length is the number of bytes the implementation accepted. An `Err` is an I/O failure from the implementation.
    fn write(&mut self, buf: &[u8]) -> io::Result<usize>;

    /// The `resize` method reports `cols` columns and `rows` rows.
    /// The grid has already changed to that size. An `Err` is an I/O failure from the implementation.
    fn resize(&mut self, cols: u16, rows: u16) -> io::Result<()>;
}

#[cfg(test)]
/// These functions check a connector that does not touch a socket.
mod tests {
    use super::Connector;

    /// @aa-sdd PK-4447 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /// The `Nop` type is a test [`Connector`] that does not touch a socket.
    /// `read` returns zero. `write` returns the buffer length. `resize` returns `Ok`.
    struct Nop;

    impl Connector for Nop {
        /// The `read` method returns zero.
        /// The buffer is not filled. The result is `Ok`.
        fn read(&mut self, _buf: &mut [u8]) -> std::io::Result<usize> {
            Ok(0)
        }

        /// The `write` method returns the length of `buf`.
        /// No bytes are sent.
        fn write(&mut self, buf: &[u8]) -> std::io::Result<usize> {
            Ok(buf.len())
        }

        /// The `resize` method returns `Ok`.
        /// The columns and the rows are ignored.
        fn resize(&mut self, _cols: u16, _rows: u16) -> std::io::Result<()> {
            Ok(())
        }
    }

    #[test]
    /// The `the_host_can_call_the_trait` test calls `write`, `read`, and `resize` on [`Nop`].
    /// `write` of two bytes returns 2. `read` returns 0. `resize` of 80 by 24 returns `Ok`.
    fn the_host_can_call_the_trait() {
        let mut nop = Nop;
        assert_eq!(nop.write(b"ab").unwrap(), 2);
        assert_eq!(nop.read(&mut [0; 4]).unwrap(), 0);
        nop.resize(80, 24).unwrap();
    }
}
