package ui;

import basis.DirectedGraph;
import basis.GraphProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseWindowControllerTest {

    /**
     * @BeforeAll 会在所有测试方法执行前运行一次。
     * 作用：为测试准备干净的、可控的图（Graph）数据。
     */
    private static BaseWindowController controller;

    @BeforeAll
    static void setUp() throws IOException {
        // 1. 创建一个临时的测试文件，写入特定的测试文本
        File tempFile = File.createTempFile("test_graph", ".txt");
        tempFile.deleteOnExit(); // 测试结束后自动删除

        try (FileWriter writer = new FileWriter(tempFile)) {
            // 这句话构建的图关系：the -> team -> wrote -> a -> detailed -> report
            writer.write("the team wrote a detailed report");
        }

        // 2. 解析文件生成图数据，并设置给 BaseWindowController 实例
        DirectedGraph graph = GraphProcessor.generateGraph(tempFile.getAbsolutePath());
        controller = new BaseWindowController();
        controller.setGraph(graph);
    }

    @Test
    void testQueryBridgeWords_Case1() {
        // 测试正常存在的桥接词
        String result = controller.queryBridgeWords("wrote", "detailed");
        assertEquals("The bridge word from \"wrote\" to \"detailed\" is: a.", result);
    }

    @Test
    void testQueryBridgeWords_Case2() {
        // 测试图中存在这两个词，但它们之间没有桥接词
        String result = controller.queryBridgeWords("report", "team");
        assertEquals("No bridge words from \"report\" to \"team\"!", result);
    }

    @Test
    void testQueryBridgeWords_Case3() {
        // 测试第一个词不在图中的情况
        String result = controller.queryBridgeWords("unknown1", "a");
        assertEquals("No \"unknown1\" in the graph!", result);
    }

    @Test
    void testQueryBridgeWords_Case4() {
        // 测试第二个词不在图中的情况
        String result = controller.queryBridgeWords("the", "unknown2");
        assertEquals("No \"unknown2\" in the graph!", result);
    }

    @Test
    void testQueryBridgeWords_Case5() {
        // 测试两个词都不在图中的情况
        String result = controller.queryBridgeWords("unknown1", "unknown2");
        assertEquals("No \"unknown1\" and \"unknown2\" in the graph!", result);
    }
}