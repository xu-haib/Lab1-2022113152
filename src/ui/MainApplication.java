package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

//对B2进行修改
//对Lab1进行若干修改，对其进行本地仓库提交操作
/**
 * MainApplication类是JavaFX应用的入口点，负责启动主窗口。
 * 它继承自Application类，加载主界面FXML文件，并设置窗口属性。
 */
public class MainApplication extends Application {
	/**
	 * JavaFX应用的启动方法，创建并显示主窗口。
	 * 加载BaseWindow.fxml布局文件，初始化控件，并设置窗口标题和大小。
	 * @param stage 主舞台（窗口）
	 * @throws Exception 如果加载FXML失败
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// 加载主窗口的FXML文件
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BaseWindow.fxml"));
		Parent root = loader.load();  // 加载根节点
		// 获取主窗口上的控件对象，用于后续操作
		MenuItem saveMI = (MenuItem) loader.getNamespace().get("saveMenuItem");  // 保存菜单项
		Button textBT = (Button) loader.getNamespace().get("textButton");  // 查看源文本按钮
		Button showBT = (Button) loader.getNamespace().get("showButton");  // 展示有向图按钮
		Button queryBT = (Button) loader.getNamespace().get("queryButton");  // 查询桥接词按钮
		Button generateBT = (Button) loader.getNamespace().get("generateButton");  // 生成新文本按钮
		Button pathBT = (Button) loader.getNamespace().get("pathButton");  // 求最短路径按钮
		Button walkBT = (Button) loader.getNamespace().get("walkButton");  // 随机游走按钮
		saveMI.setDisable(true);  // 初始禁用保存菜单，因为还没生成图
		// 创建场景并设置到舞台
		Scene scene = new Scene(root);
		stage.setTitle("软件工程实验一");  // 设置窗口标题
		stage.setScene(scene);
		stage.setResizable(true);  // 允许调整大小
		stage.setMinWidth(950);  // 最小宽度
		stage.setMinHeight(650);  // 最小高度
		stage.show();  // 显示窗口
	}
	
	/**
	 * 主方法，启动JavaFX应用。
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		launch(args);  // 调用JavaFX的launch方法启动应用
	}
}
