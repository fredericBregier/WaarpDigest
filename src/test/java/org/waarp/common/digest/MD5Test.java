/*******************************************************************************
 * This file is part of Waarp Project (named also Waarp or GG).
 *
 *  Copyright (c) 2019, Waarp SAS, and individual contributors by the @author
 *  tags. See the COPYRIGHT.txt in the distribution for a full listing of
 *  individual contributors.
 *
 *  All Waarp Project is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or (at your
 *  option) any later version.
 *
 *  Waarp is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 *  A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  Waarp . If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package org.waarp.common.digest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class MD5Test {

  @Test
  public void updateTest() throws UnsupportedEncodingException {
    String totest = "MonTest";
    MD5 md5 = new MD5(totest);
    assertArrayEquals(md5.Final(), MD5.asByte(md5.asHex()));
    MD5 md52 = new MD5();
    md52.Update(totest);
    assertTrue(MD5.hashesEqual(md5.Final(), md52.Final()));
    MD5 md53 = new MD5();
    byte[] btotest = totest.getBytes();
    md53.Update(btotest, btotest.length);
    assertTrue(MD5.hashesEqual(md5.Final(), md53.Final()));
    MD5 md54 = new MD5();
    md54.Update(btotest);
    assertTrue(MD5.hashesEqual(md5.Final(), md54.Final()));
    MD5 md55 = new MD5();
    md55.Update(totest, "UTF-8");
    assertTrue(MD5.hashesEqual(md5.Final(), md55.Final()));
    MD5 md56 = new MD5();
    for (int i = 0; i < btotest.length; i++) {
      md56.Update(btotest[i]);
    }
    assertTrue(MD5.hashesEqual(md5.Final(), md56.Final()));
    MD5 md57 = new MD5();
    for (int i = 0; i < btotest.length; i++) {
      md57.Update((int) btotest[i]);
    }
    assertTrue(MD5.hashesEqual(md5.Final(), md57.Final()));
    ByteBuf buf = Unpooled.wrappedBuffer(btotest);
    MD5 md58 = new MD5();
    md58.Update(buf);
    assertTrue(MD5.hashesEqual(md5.Final(), md58.Final()));
  }

  @Test
  public void passwordTest() {
    String passwd = "totest";
    String pwd = MD5.passwdCrypt(passwd);
    assertTrue(MD5.equalPasswd(passwd, pwd));
    byte[] bpwd = MD5.passwdCrypt(passwd.getBytes());
    assertTrue(MD5.equalPasswd(passwd.getBytes(), bpwd));
    assertTrue(MD5.hashesEqual(MD5.asByte(pwd), bpwd));
  }


}