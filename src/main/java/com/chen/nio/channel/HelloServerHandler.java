package com.chen.nio.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class HelloServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			ByteBuf in = (ByteBuf) msg;
			System.out.print(in.toString(CharsetUtil.UTF_8));
		} finally {
			// �����յ������ݣ�����֤����
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// �������쳣�͹ر�����
		cause.printStackTrace();
		ctx.close();
	}

}
