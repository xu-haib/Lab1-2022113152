package ui;

import basis.DirectedGraph;
import basis.GraphProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class calShortestPathTest {
    private BaseWindowController controller;

    @BeforeEach
    void setUp() throws IOException {
        controller = new BaseWindowController();

        // 1. 构建严格匹配测试用例场景的文本源数据
        String text = "the scientist carefully analyzed the data wrote a detailed report " +
                "and shared the report with the team but the team requested more data " +
                "so the scientist analyzed it again";

        // 2. 创建临时文件并写入文本
        File tempFile = File.createTempFile("testGraph", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(text);
        }

        // 3. 生成图对象并注入到 Controller 中，防止 NullPointerException
        DirectedGraph graph = GraphProcessor.generateGraph(tempFile.getAbsolutePath());
        controller.setGraph(graph);

        // 测试结束后自动删除临时文件
        tempFile.deleteOnExit();
    }

    @Test
    void testCase1_NodeNotFound() {
        // 覆盖路径：1 (起终点缺失)
        String result = controller.calcShortestPath("apple", "data");
        assertEquals("No apple or data in the graph!", result);
    }

    @Test
    void testCase2_ShorterPath() {
        // 覆盖路径：2, 3, 6 (Dijkstra 发现更优路径并更新)
        // the->scientist(权重2) + scientist->analyzed(权重1) = 距离3
        String result = controller.calcShortestPath("the", "analyzed");

        // 双重断言：既验证长度，也验证具体经过的节点路线
        assertTrue(result.contains("Length: 3"), "The length should be 3");
        assertTrue(result.contains("the -> scientist -> analyzed"), "Path should match the exact node sequence");
    }

    @Test
    void testCase3_EqualPath() {
        // 覆盖路径：4 (Dijkstra 发现等长路径)
        // 存在两条长度均为 3 的最短路：
        // 1. team -> but -> the -> data
        // 2. team -> requested -> more -> data
        String result = controller.calcShortestPath("team", "data");

        assertTrue(result.contains("Length: 3"), "The length of equal paths should be 3");
        // 因为具体输出哪一条取决于 HashMap 的遍历顺序，所以只要验证起终点和中间任意有效节点即可
        assertTrue(result.startsWith("Path: team") && result.contains("data"), "Path should start with team and end with data");
    }

    @Test
    void testCase4_MinNodeSelection() {
        // 覆盖路径：5, 6 (在候选集中寻找最短的一跳)
        String result = controller.calcShortestPath("scientist", "data");

        assertTrue(result.contains("Length: 3"), "The length should be 3");
        assertTrue(result.contains("scientist -> analyzed -> the -> data"), "Path should match the min node sequence");
    }

    @Test
    void testCase5_Unreachable() {
        // 覆盖路径：7 (无法连通，距离保持无穷大)
        // "again" 是文本最后一个单词，没有出边
        String result = controller.calcShortestPath("again", "the");
        assertEquals("No path from again to the!", result);
    }

    @Test
    void testCase6_NormalSuccess() {
        // 覆盖路径：8 (正常的、无争议的最短路径搜索)
        // wrote -> a -> detailed -> report (距离3)
        String result = controller.calcShortestPath("wrote", "report");

        assertTrue(result.contains("Length: 3"), "The length should be 3");
        assertTrue(result.contains("wrote -> a -> detailed -> report"), "Path should be exactly matched");
    }
}