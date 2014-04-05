package com.gerenhua.tool.utils;

import java.awt.Window;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class SwingUtils {
	public static void updateUI() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Window windows[] = Window.getWindows();
				for (int i = 0; i < windows.length; i++) {
					if (windows[i].isDisplayable()) {
						SwingUtilities.updateComponentTreeUI(windows[i]);
					}
				}
			}
		});
	}

	public static void restart(String appName) throws IOException {
		// 用一条指定的命令去构造一个进程生成器
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", appName + ".jar");
		// 让这个进程的工作区空间改为F:\dist
		// 这样的话,它就会去F:\dist目录下找Test.jar这个文件
		// pb.directory(new File("F:\\dist"));
		// 得到进程生成器的环境 变量,这个变量我们可以改,
		// 改了以后也会反应到新起的进程里面去
		// Map<String, String> map = pb.environment();
		Process p = pb.start();
		// 然后就可以对p做自己想做的事情了
		// 自己这个时候就可以退出了
		System.exit(0);
	}

	/**
	 * setTheme
	 * 
	 * @param lafIndex
	 * @param theme
	 */
	public static void setTheme(int lafIndex, String theme) {
		try {
			switch (lafIndex) {
			case 0:
				com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme(theme);
				break;
			case 1:
				com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme(theme);
				break;
			case 2:
				com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme(theme);
				break;
			case 3:
				com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme(theme);
				break;
			case 4:
				com.jtattoo.plaf.fast.FastLookAndFeel.setTheme(theme);
				break;
			case 5:
				com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme(theme);
				break;
			case 6:
				com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme(theme);
				break;
			case 7:
				com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme(theme);
				break;
			case 8:
				com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme(theme);
				break;
			case 9:
				com.jtattoo.plaf.mint.MintLookAndFeel.setTheme(theme);
				break;
			case 10:
				com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme(theme);
				break;
			case 11:
				com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme(theme);
				break;
			case 12:
				com.jtattoo.plaf.texture.TextureLookAndFeel.setTheme(theme);
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
