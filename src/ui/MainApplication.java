package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

// 对 B2 进行修改
// 对 Lab1 进行若干修改，对其进行本地仓库提交操作
/**
 * MainApplication 类是 JavaFX 应用的入口点，负责启动主窗口.
 * 它继承自 Application 类，加载主界面 FXML 文件，并设置窗口属性.
 */
public class MainApplication extends Application {
  /**
   * JavaFX 应用的启动方法，创建并显示主窗口.
   * 加载 BaseWindow.fxml 布局文件，初始化控件，并设置窗口标题和大小.
   *
   * @param stage 主舞台（窗口）
   * @throws Exception 如果加载 FXML 失败.
   */
  @Override
  public void start(Stage stage) throws Exception {
    // 加载主窗口的 FXML 文件
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BaseWindow.fxml"));
    Parent root = loader.load();

    // 获取主窗口上的控件对象，用于后续操作
    MenuItem saveMenuItem = (MenuItem) loader.getNamespace().get("saveMenuItem");

    saveMenuItem.setDisable(true);

    // 创建场景并设置到舞台
    Scene scene = new Scene(root);
    stage.setTitle("软件工程实验一");
    stage.setScene(scene);
    stage.setResizable(true);
    stage.setMinWidth(950);
    stage.setMinHeight(650);
    stage.show();
  }

  /**
   * 主方法，启动 JavaFX 应用.
   *
   * @param args 命令行参数
   */
  public static void main(String[] args) {
    launch(args);
  }
}
