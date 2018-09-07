package com.chen.nio.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

	private int port;

	public HelloServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // �������ս���������
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // ���������Ѿ������յ�����
		System.out.println("׼�����ж˿ڣ�" + port);

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // �������Channel��ν����µ�����
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// �Զ��崦����
							ch.pipeline().addLast(new HelloServerHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

			// �󶨶˿ڣ���ʼ���ս���������
			ChannelFuture f = b.bind(port).sync();

			// �ȴ�������socket�ر�
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 10110;
		new HelloServer(port).run();
		// ͨ�����ӱ���telnet 127.0.0.1 10110���з���
	}

}
