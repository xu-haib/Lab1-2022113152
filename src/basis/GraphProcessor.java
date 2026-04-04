package basis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

/**
 * GraphProcessor类是一个工具类，提供静态方法来处理图的生成和文本解析。
 * 它不存储状态，所有方法都是静态的，用于从文件生成有向图和处理文本。
 */
public class GraphProcessor {
	/**
	 * 根据文件路径生成有向图。
	 * 读取文件内容，将连续的单词作为顶点和边添加到图中。
	 * 例如，文本"hello world java" 会创建顶点hello, world, java，并添加边hello->world, world->java。
	 * @param fileName 文件的路径和名称
	 * @return 生成的有向图对象
	 */
	public static DirectedGraph generateGraph(String fileName) {
		Scanner in;  // 用于读取文件的扫描器
		String pre, post;  // pre存储前一个单词，post存储当前单词
		DirectedGraph graph = new DirectedGraph();  // 创建一个新的有向图
		try {
			in = new Scanner(new FileInputStream(fileName));  // 打开文件输入流
			// 读取第一个有效的单词作为起点
			do {
				pre = parseText(in.next());  // 解析下一个单词				
			} while (pre == null && in.hasNext());  // 如果解析失败且还有内容，继续读取
			if (pre != null) {
				graph.addVertex(pre);  // 将第一个单词添加到图中
			}
			// 继续读取后续单词，添加顶点和边
			while (in.hasNext()) {  // 只要文件还有内容
				post = parseText(in.next());  // 解析下一个单词
				if (post != null) {  // 如果解析成功
					graph.addVertex(post);  // 添加新单词为顶点
					graph.addEdge(pre, post);  // 添加从前一个单词到当前单词的边
					pre = post;  // 更新前一个单词为当前单词
				}
			}
		} catch (FileNotFoundException e) {
			System.exit(0);  // 如果文件找不到，退出程序
		}
		return graph;  // 返回构建好的图
	}

	/**
	 * 处理字符串中的非法字符，只保留字母并转换为小写。
	 * 用于过滤文本中的标点、数字等，只保留单词。
	 * @param str 待处理的原始字符串
	 * @return 处理后的纯字母小写字符串，如果全是非法字符则返回null
	 */
	public static String parseText(String str) {
		StringBuffer sb = new StringBuffer();  // 用于构建结果字符串
		if (str != null) {  // 如果输入字符串不为空
			for (int i = 0; i < str.length(); ++i) {  // 遍历字符串的每个字符
				char c = str.charAt(i);  // 获取当前字符
				if (Character.isLetter(c)) {  // 如果是字母
					sb.append(Character.toLowerCase(c));  // 转换为小写并添加到结果中
				}
			}
		}
		return (sb.toString().equals("")) ? null : sb.toString();  // 如果结果为空，返回null，否则返回结果
	}

	/**
	 * 从集合中随机选择一个元素。
	 * 用于随机游走等需要随机性的地方。
	 * @param set 要从中选择的集合
	 * @return 随机选择的元素，如果集合为空或null则返回null
	 */
	public static Vertex randomSelect(Collection<Vertex> set) {
		if (set == null || set.size() == 0) {  // 如果集合为空或null
			return null;  // 返回null
		}
		int item = new Random().nextInt(set.size());  // 生成随机索引
		int i = 0;  // 计数器
		for (Vertex v : set) {  // 遍历集合
			if (i == item) {  // 如果索引匹配
				return v;  // 返回该元素
			}
			++i;  // 计数器增加
		}
		return null;  // 理论上不会到达这里，但为了安全返回null
	}
}
