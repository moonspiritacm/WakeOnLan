import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class WakeOnLan {

	public static void main(String[] args) {
		String ip = "255.255.255.255"; // 广播地址
		int port = 9; // 端口号

		Scanner sc = new Scanner(System.in);
		System.out.print("目标主机 MAC 地址：");
		String mac = sc.nextLine();
		sc.close();
		// 0xFFFFFFFFFFFF + MAC*16
		String magicPacage = "0xFFFFFFFFFFFF" + mac + mac + mac + mac + mac + mac + mac + mac + mac + mac + mac + mac
				+ mac + mac + mac + mac;
		byte[] command = hexToBinary(magicPacage);

		try {
			InetAddress address = InetAddress.getByName(ip);
			MulticastSocket socket = new MulticastSocket(port);
			DatagramPacket packet = new DatagramPacket(command, command.length, address, port);
			socket.send(packet);
			socket.close();
		} catch (Exception e) {
			System.err.println("WakeOnLan.main(): " + e.toString());
		}
	}

	/**
	 * 将16进制字符串转换为用byte数组表示的二进制形式
	 * 
	 * @param hexString：16进制字符串
	 * @return：用byte数组表示的十六进制数
	 */
	private static byte[] hexToBinary(String hexString) {
		byte[] result = new byte[hexString.length()];
		hexString = hexString.toUpperCase().replace("0X", "");

		char tmp1 = '0';
		char tmp2 = '0';
		for (int i = 0; i < hexString.length(); i += 2) {
			result[i / 2] = (byte) ((hexToDec(tmp1) << 4) | (hexToDec(tmp2)));
		}
		return result;
	}

	/**
	 * 用于将16进制的单个字符映射到10进制的方法
	 * 
	 * @param c：16进制数的一个字符
	 * @return：对应的十进制数
	 */
	private static byte hexToDec(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
