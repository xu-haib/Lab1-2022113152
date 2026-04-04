package basis;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * RandomWalker类实现随机游走功能，模拟在有向图上随机移动的过程。
 * 它实现了Runnable接口，可以作为线程运行，用于实时显示游走路径。
 * 随机游走从一个随机顶点开始，每次随机选择一个后继顶点，直到遇到重复边或无后继。
 */
public class RandomWalker implements Runnable {
    private TextArea area;  // 用于显示随机游走结果的文本区域（UI组件）
    private ArrayList<Vertex> vertices;  // 图中所有顶点的列表，用于初始化
    private Thread thread;  // 控制随机游走的线程
    private boolean suspended;  // 标记线程是否暂停

    /**
     * 构造函数，初始化随机游走器。
     * @param a 显示结果的文本区域
     * @param l 图的顶点列表
     */
    public RandomWalker(TextArea a, ArrayList<Vertex> l) {
        this.area = a;  // 设置显示区域
        this.vertices = l;  // 设置顶点列表
    }

    /**
     * 线程的运行方法，实现随机游走逻辑。
     * 从随机顶点开始，每秒移动一次，记录路径，直到停止条件满足。
     */
    @Override
    public void run() {
        // 用于记录已被游走过的边，避免重复访问同一边
        HashMap<Vertex, HashSet<Vertex>> walkedEdges = new HashMap<>();
        for (Vertex v : vertices) {  // 为每个顶点初始化一个空集合
            walkedEdges.put(v, new HashSet<>());
        }
        // 随机选择起始顶点
        Vertex current = vertices.get(new Random().nextInt(vertices.size()));
        Vertex nextVertex;  // 下一个要访问的顶点
        StringBuffer sb = new StringBuffer();  // 构建游走路径的字符串
        sb.append(current.name);  // 添加起始顶点名称
        area.setText(sb.toString());  // 显示初始路径
        while (true) {  // 无限循环，直到满足停止条件
            try {
                Thread.sleep(1000);  // 暂停1秒，模拟游走间隔
                // 线程同步：如果暂停，等待恢复
                synchronized (this) {
                    while (suspended) {  // 如果被暂停
                        wait();  // 等待notify()唤醒
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  // 打印中断异常
            }
            // 随机选择当前顶点的后继顶点
            nextVertex = GraphProcessor.randomSelect(current.successors);
            // 如果没有后继顶点，停止游走
            if (nextVertex == null) {
                break;  // 退出循环
            }
            // 更新显示路径
            sb.append(" " + nextVertex.name);  // 添加新顶点到路径
            area.setText(sb.toString());  // 更新UI显示
            // 如果这条边已被游走过，停止游走
            if (walkedEdges.get(current).contains(nextVertex)) {
                break;  // 退出循环
            }
            // 记录这条边已被游走
            walkedEdges.get(current).add(nextVertex);
            // 移动到下一个顶点
            current = nextVertex;
        }
    }

    /**
     * 启动随机游走线程。
     * 如果线程不存在，创建新线程并启动。
     */
    public void start() {
        if (thread == null) {  // 如果线程未创建
            thread = new Thread(this, "RandomWalkThread");  // 创建线程，命名为RandomWalkThread
            thread.start();  // 启动线程
        }
    }

    /**
     * 暂停随机游走线程。
     * 设置暂停标志，下次循环时会等待。
     */
    public void suspend() {
        suspended = true;  // 设置暂停为true
    }

    /**
     * 恢复随机游走线程。
     * 清除暂停标志，并唤醒等待的线程。
     */
    public synchronized void resume() {
        suspended = false;  // 设置暂停为false
        notify();  // 唤醒等待的线程
    }
}
