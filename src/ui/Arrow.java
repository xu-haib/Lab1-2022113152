package ui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;

/**
 * Arrow类表示有向图中边的UI组件，继承自Group。
 * 它由一条直线、一个三角形箭头和一个文本标签组成，用于显示边的方向和权重。
 * 箭头会根据直线的起点和终点自动调整位置和角度。
 */
public class Arrow extends Group {
    private Line line;  // 箭头的直线部分，表示边的主体
    private static final double arrow_size = 8;  // 箭头三角形的大小常量

    /**
     * 构造函数，创建带权重的箭头。
     * @param line 箭头的直线部分
     * @param weight 边的权重，用于显示在箭头上
     */
    public Arrow(Line line, int weight) {
        this(line, new Polygon(), new Text(String.valueOf(weight)));  // 调用私有构造函数，创建三角形和文本
    }

    /**
     * 私有构造函数，初始化箭头的各个部分。
     * 计算箭头的初始位置和角度，并设置监听器以便动态更新。
     * @param line 箭头的直线部分
     * @param triangle 箭头的三角形部分
     * @param text 箭头的标签部分（显示权重）
     */
    private Arrow(Line line, Polygon triangle, Text text) {
        super(line, triangle, text);  // 调用父类Group的构造函数，添加所有子组件
        this.line = line;  // 保存直线引用

        // 获取直线的初始起点和终点坐标
        double sxInit = getStartX();  // 直线起点X
        double syInit = getStartY();  // 直线起点Y
        double exInit = getEndX();    // 直线终点X
        double eyInit = getEndY();    // 直线终点Y

        // 计算直线的方向向量和角度
        double dxInit = exInit - sxInit;  // X方向差
        double dyInit = eyInit - syInit;  // Y方向差
        double angleInit = Math.atan2(dyInit, dxInit);  // 计算角度（弧度）

        // 创建初始变换：先平移到终点，再旋转
        Transform transInit = Transform.translate(exInit, eyInit);  // 平移到终点
        transInit = transInit.createConcatenation(Transform.rotate(Math.toDegrees(angleInit), 0, 0));  // 旋转到正确角度

        // 设置三角形箭头的形状和位置
        triangle.getPoints().clear();  // 清空原有点
        triangle.getPoints().addAll(  // 添加三角形顶点（相对于原点0,0）
                0.0, 0.0,  // 箭头顶点
                -arrow_size, arrow_size / 2,  // 左下点
                -arrow_size, -arrow_size / 2);  // 右下点
        triangle.getTransforms().clear();  // 清空变换
        triangle.getTransforms().add(transInit);  // 应用变换
        triangle.setFill(Color.DARKBLUE);  // 设置填充色为深蓝色

        // 设置文本标签的属性
        text.setWrappingWidth(40);  // 设置文本宽度
        text.setTextAlignment(TextAlignment.CENTER);  // 居中对齐
        text.setTextOrigin(VPos.CENTER);  // 垂直居中
        text.setFill(Color.DARKBLUE);  // 设置颜色为深蓝色
        // 设置文本位置为直线中点
        text.setLayoutX((sxInit + exInit) / 2 - text.getWrappingWidth() / 2);
        text.setLayoutY((syInit + eyInit) / 2 - text.getScaleY());

        // 创建监听器，当直线位置变化时更新箭头和文本
        InvalidationListener updater = o -> {
            // 获取当前直线位置
            double sx = getStartX();
            double sy = getStartY();
            double ex = getEndX();
            double ey = getEndY();

            // 重新计算方向和角度
            double dx = ex - sx;
            double dy = ey - sy;
            double angle = Math.atan2(dy, dx);

            // 创建新变换
            Transform transform = Transform.translate(ex, ey);
            transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));

            // 更新三角形
            triangle.getPoints().clear();
            triangle.getPoints().addAll(
                    0.0, 0.0,
                    -arrow_size, arrow_size / 2,
                    -arrow_size, -arrow_size / 2);
            triangle.getTransforms().clear();
            triangle.getTransforms().add(transform);
            triangle.setFill(Color.PURPLE);

            // 更新文本位置
            text.setLayoutX((sx + ex) / 2 - text.getWrappingWidth() / 2);
            text.setLayoutY((sy + ey) / 2 - text.getScaleY());
        };

        // 将监听器绑定到直线的各个属性
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
    }

    /**
     * 设置箭头直线的颜色。
     * @param value 新的颜色值
     */
    public void setStroke(Paint value) {
        this.line.setStroke(value);
    }

    /**
     * 设置箭头直线的宽度。
     * @param value 新的宽度值
     */
    public void setStrokeWidth(double value) {
        this.line.setStrokeWidth(value);
    }

    /**
     * 设置直线的起点X坐标。
     * @param value 新的起点X值
     */
    public final void setStartX(double value) {
        line.setStartX(value);
    }

    /**
     * 获取直线的起点X坐标。
     * @return 起点X坐标
     */
    public final double getStartX() {
        return line.getStartX();
    }

    /**
     * 获取直线起点X坐标的属性对象，用于绑定。
     * @return DoubleProperty对象
     */
    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    /**
     * 设置直线的起点Y坐标。
     * @param value 新的起点Y值
     */
    public final void setStartY(double value) {
        line.setStartY(value);
    }

    /**
     * 获取直线的起点Y坐标。
     * @return 起点Y坐标
     */
    public final double getStartY() {
        return line.getStartY();
    }

    /**
     * 获取直线起点Y坐标的属性对象，用于绑定。
     * @return DoubleProperty对象
     */
    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    /**
     * 设置直线的终点X坐标。
     * @param value 新的终点X值
     */
    public final void setEndX(double value) {
        line.setEndX(value);
    }

    /**
     * 获取直线的终点X坐标。
     * @return 终点X坐标
     */
    public final double getEndX() {
        return line.getEndX();
    }

    /**
     * 获取直线终点X坐标的属性对象，用于绑定。
     * @return DoubleProperty对象
     */
    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    /**
     * 设置直线的终点Y坐标。
     * @param value 新的终点Y值
     */
    public final void setEndY(double value) {
        line.setEndY(value);
    }

    /**
     * 获取直线的终点Y坐标。
     * @return 终点Y坐标
     */
    public final double getEndY() {
        return line.getEndY();
    }

    /**
     * 获取直线终点Y坐标的属性对象，用于绑定。
     * @return DoubleProperty对象
     */
    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }
}
