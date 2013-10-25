package net.shopxx.common;
import org.compass.gps.CompassGps;
import org.springframework.beans.factory.InitializingBean;

/**
 * 通过quartz调度定时重建索引或自动随Spring ApplicationContext启动而重建索引
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX13D156FE217D4020D96236A46E1DB023
 * ============================================================================
 */

public class CompassIndexBuilder implements InitializingBean {

	private int delayTime = 60;// 索引操作线程延时,单位: 秒
	private CompassGps compassGps;

	private Thread indexThread = new Thread() {
		public void run() {
			try {
				Thread.sleep(delayTime * 1000);
				compassGps.index();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public void afterPropertiesSet() throws Exception {
		indexThread.setDaemon(true);
		indexThread.setName("Compass Indexer");
		indexThread.start();
	}

	public void index() {
		compassGps.index();
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	public CompassGps getCompassGps() {
		return compassGps;
	}

	public void setCompassGps(CompassGps compassGps) {
		this.compassGps = compassGps;
	}

}