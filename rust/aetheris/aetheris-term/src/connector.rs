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
//! @author clayl (2026-10-23)

use std::io;

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
