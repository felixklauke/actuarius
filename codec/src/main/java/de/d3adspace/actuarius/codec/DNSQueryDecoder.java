/*
 * MIT License
 *
 * Copyright (c) 2017 - 2019 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.d3adspace.actuarius.codec;

import de.d3adspace.actuarius.protocol.DNSMessageType;
import de.d3adspace.actuarius.protocol.DNSOperationCode;
import de.d3adspace.actuarius.protocol.DNSQuery;
import de.d3adspace.actuarius.protocol.DNSQueryImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class DNSQueryDecoder extends MessageToMessageDecoder<DatagramPacket> {

    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf packetContent = datagramPacket.content();
        DNSQuery query = decodeQuery(packetContent, datagramPacket);

        out.add(query);
    }

    private DNSQuery decodeQuery(ByteBuf packetContent, DatagramPacket datagramPacket) {
        int packetId = packetContent.readUnsignedShort();
        int flagsContainer = packetContent.readUnsignedShort();

        DNSOperationCode operationCode = getOperationCode(flagsContainer);
        DNSMessageType messageType = getMessageType(flagsContainer);

        return new DNSQueryImpl(packetId, messageType, operationCode, false);
    }

    private DNSMessageType getMessageType(int flagsContainer) {
        return DNSMessageType.getTypeViaCode(flagsContainer >> 15);
    }

    private DNSOperationCode getOperationCode(int flagsContainer) {
        return DNSOperationCode.getCodeViaId((byte) (flagsContainer >> 11 & 0xf));
    }
}
