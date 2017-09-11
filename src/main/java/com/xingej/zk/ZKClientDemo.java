package com.xingej.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZKClientDemo {
	public static void main(String[] args) {
		String zkServers = "192.168.1.110:2181,192.168.1.119:2181,192.168.1.120:2181";
		int connectionTimeout = 3000;
		ZkClient zkClient = new ZkClient(zkServers, connectionTimeout);
		String path = "/zk-data";
		// 若节点已经存在，则删除
		if (zkClient.exists(path)) {
			zkClient.delete(path);
		}

		// 创建持久节点
		zkClient.createPersistent(path);
		//
		// 节点写入数据
		zkClient.writeData(path, "test_data_1");

		// 节点读取数据
		String data = zkClient.readData(path, true);
		System.out.println(data);

		// 注册监听器，监听数据变化
		zkClient.subscribeDataChanges(path, new IZkDataListener() {

			@Override
			public void handleDataDeleted(String data) throws Exception {
				System.out.println("------>handleDataDeleted, dataPath:" + data);
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("----->handleDataChange, dataPath:\t" + dataPath + " data:\t" + data);
			}
		});

		// 修改数据,将test_data_1 改为了test_data_2了
		zkClient.writeData(path, "test_data_2");

		// 节点读取数据
		String newData = zkClient.readData(path, true);
		System.out.println("----该节点下最新的值是----:\t" + newData);

		// 删除节点
		zkClient.delete(path);

	}
}
