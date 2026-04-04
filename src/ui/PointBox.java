package ui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * PointBox类表示图中顶点的UI组件，继承自StackPane。
 * 它包含一个圆形（代表顶点）和文本（显示顶点名称），支持拖拽和颜色变化。
 * 中心坐标通过属性绑定自动计算，便于箭头连接。
 */
public class PointBox extends StackPane {
    // 顶点中心的X坐标属性，通过绑定自动计算
    private DoubleProperty centerX = new SimpleDoubleProperty();
    // 顶点中心的Y坐标属性，通过绑定自动计算
    private DoubleProperty centerY = new SimpleDoubleProperty();

    /**
     * 默认构造函数，初始化PointBox。
     * 设置中心坐标属性与布局属性的绑定：中心X = 左上角X + 宽度/2，中心Y = 左上角Y + 高度/2。
     */
    public PointBox() {
        super();  // 调用父类StackPane的构造函数
        // 绑定中心X属性：等于布局X位置加上宽度的一半
        centerX.bind(this.layoutXProperty().add(this.prefWidthProperty().divide(2)));
        // 绑定中心Y属性：等于布局Y位置加上高度的一半
        centerY.bind(this.layoutYProperty().add(this.prefHeightProperty().divide(2)));
    }

    /**
     * 获取顶点中心的X坐标。
     * @return 中心X坐标值
     */
    public final double getCenterX() {
        return this.centerX.get();  // 返回属性值
    }

    /**
     * 设置顶点中心的X坐标。
     * @param value 新的X坐标值
     */
    public final void setCenterX(double value) {
        this.centerX.set(value);  // 设置属性值
    }

    /**
     * 获取顶点中心的Y坐标。
     * @return 中心Y坐标值
     */
    public final double getCenterY() {
        return this.centerY.get();  // 返回属性值
    }

    /**
     * 设置顶点中心的Y坐标。
     * @param value 新的Y坐标值
     */
    public final void setCenterY(double value) {
        this.centerY.set(value);  // 设置属性值
    }

    /**
     * 获取中心X坐标的属性对象，用于绑定。
     * @return DoubleProperty对象
     */
    public DoubleProperty centerXProperty() {
        return centerX;  // 返回属性引用
    }

    /**
     * 获取中心Y坐标的属性对象，用于绑定。
     * @return DoubleProperty对象
     */
    public DoubleProperty centerYProperty() {
        return centerY;  // 返回属性引用
    }

    /**
     * 获取PointBox内部的Circle形状对象（顶点的圆形表示）。
     * 遍历子节点，找到Circle对象。
     * @return Circle对象，如果不存在返回null
     */
    public Circle getCircle() {
        for (Node node : this.getChildren()) {  // 遍历所有子节点
            if (node instanceof Circle) {  // 如果是Circle类型
                return (Circle) node;  // 返回该Circle
            }
        }
        return null;  // 如果没找到，返回null
    }

    /**
     * 获取PointBox内部的Text文本对象（顶点名称）。
     * 遍历子节点，找到Text对象。
     * @return Text对象，如果不存在返回null
     */
    public Text getText() {
        for (Node node : this.getChildren()) {  // 遍历所有子节点
            if (node instanceof Text) {  // 如果是Text类型
                return (Text) node;  // 返回该Text
            }
        }
        return null;  // 如果没找到，返回null
    }
}
