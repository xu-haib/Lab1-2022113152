package basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//对提交的部分文件进行修改
/**
 * DirectedGraph类表示一个有向图（Directed Graph）。
 * 有向图是由顶点（Vertex）和有向边（Edge）组成的图结构，每条边有方向，从一个顶点指向另一个。
 * 在这个项目中，有向图用来表示单词之间的关系，比如文本中单词的先后顺序。
 * 例如，如果文本是"hello world"，则有边 hello -> world。
 */
public class DirectedGraph {
	// 存储图中所有顶点的列表，使用ArrayList便于遍历和索引
	private ArrayList<Vertex> vertices;

	/**
	 * 默认构造函数，创建一个空的有向图。
	 * 初始化顶点列表为空，等待后续添加顶点和边。
	 */
	public DirectedGraph() {
		this.vertices = new ArrayList<>();  // 创建一个空的顶点列表
	}

	/**
	 * 获取图中所有顶点的列表。
	 * @return 顶点列表的引用，外部可以遍历但不应该修改（除非通过类方法）
	 */
	public ArrayList<Vertex> getVertices() {
		return this.vertices;  // 返回顶点列表
	}

	/**
	 * 获取图中顶点的数量。
	 * @return 顶点总数
	 */
	public int getVertexNumber() {
		return this.vertices.size();  // 返回列表大小
	}

	/**
	 * 计算图中边的总数。
	 * 由于是有向图，每条边从一个顶点指向另一个，所以统计所有顶点的出边数。
	 * @return 边的总数
	 */
	public int getEdgeNumber() {
		int number = 0;  // 初始化边数为0
		for (Vertex v : vertices) {  // 遍历每个顶点
			number += v.successors.size();  // 累加每个顶点的后继数量（出边数）
		}
		return number;  // 返回总边数
	}

	/**
	 * 向有向图中添加顶点，如果顶点已存在则不添加。
	 * 这样避免重复添加相同的单词。
	 * @param name 顶点名称，通常是一个单词
	 */
	public void addVertex(String name) {
		if (findVertex(name) != null) {  // 检查顶点是否已存在
			return;  // 如果存在，不添加
		}
		vertices.add(new Vertex(name));  // 创建新顶点并添加到列表
	}

	/**
	 * 向有向图中添加有向边，如果边已存在则增加权重。
	 * 权重表示边的强度，比如单词对出现的次数。
	 * @param head 起点顶点的名称
	 * @param tail 终点顶点的名称
	 * @throws IllegalArgumentException 如果顶点不存在
	 */
	public void addEdge(String head, String tail) {
		Vertex headVertex = findVertex(head);  // 查找起点顶点
		Vertex tailVertex = findVertex(tail);  // 查找终点顶点
		if (headVertex == null || tailVertex == null) {
			throw new IllegalArgumentException("Vertex not found: " + head + " or " + tail);  // 抛出异常，如果顶点不存在
		}
		if (headVertex.successors.contains(tailVertex)) {  // 检查边是否已存在
			int weight = headVertex.weights.get(tailVertex);  // 获取当前权重
			headVertex.weights.replace(tailVertex, weight + 1);  // 增加权重
		} else {
			headVertex.successors.add(tailVertex);  // 添加到后继集合
			tailVertex.predecessors.add(headVertex);  // 添加到前驱集合
			headVertex.weights.put(tailVertex, 1);  // 初始化权重为1
		}
	}

	/**
	 * 根据名称查找顶点。
	 * 遍历顶点列表，找到名称匹配的顶点。
	 * @param name 要查找的顶点名称
	 * @return 找到的顶点对象，如果不存在返回null
	 */
	private Vertex findVertex(String name) {
		for (Vertex v : vertices) {  // 遍历所有顶点
			if (v.name.equals(name)) {  // 检查名称是否匹配
				return v;  // 返回匹配的顶点
			}
		}
		return null;  // 如果没找到，返回null
	}
	
	@Override
	public String toString() {
		String result = "";  // 初始化结果字符串为空
		for (Vertex v : vertices) {  // 遍历图中的每一个顶点
			result += v.toString() + ": " + v.successors.toString() + "\n";  // 将顶点名称和其后继列表添加到结果中，每行一个顶点
		}
		return result;  // 返回完整的字符串描述
	}
	
	@Override
	public int hashCode() {
		int code = 0;  // 初始化哈希码为0
		for (Vertex v : vertices) {  // 遍历图中的每一个顶点
			code += v.hashCode();  // 将每个顶点的哈希码累加到总哈希码中
		}
		return code;  // 返回累加后的哈希码，用于对象比较
	}
	
	/**
	 * 计算PageRank值，使用幂迭代法。
	 * PageRank是一种算法，用于衡量网页（或节点）的重要性，模拟用户随机浏览网页的行为。
	 * 在这里，用于计算单词在文本中的重要性。
	 * @param d 阻尼因子，通常为0.85，表示用户继续浏览的概率（而不是随机跳转）
	 * @param maxIterations 最大迭代次数，避免无限循环，通常100次足够收敛
	 * @return 每个节点的PageRank值映射，键为顶点，值为PR值（0到1之间的数）
	 */
	public Map<Vertex, Double> computePageRank(double d, int maxIterations) {
		int N = vertices.size();  // 获取图中顶点的总数N
		if (N == 0) return new HashMap<>();  // 如果图中没有顶点，返回空的映射
		
		Map<Vertex, Double> pr = new HashMap<>();  // 创建一个映射来存储每个顶点的当前PR值
		// 初始化PR值，每个节点初始PR值为1/N（平均分配）
		for (Vertex v : vertices) {
			pr.put(v, 1.0 / N);  // 设置每个顶点的初始PR值为平均值
		}
		
		// 计算出度（每个顶点的出边数），用于后续PR计算
		Map<Vertex, Integer> outDegrees = new HashMap<>();
		for (Vertex v : vertices) {
			outDegrees.put(v, v.successors.size());  // 记录每个顶点的出度（后继数量）
		}
		
		// 迭代计算PR值，直到收敛或达到最大次数
		for (int iter = 0; iter < maxIterations; iter++) {  // 循环进行迭代，每次更新PR值
			Map<Vertex, Double> newPr = new HashMap<>();  // 创建新映射存储新一轮的PR值
			
			// 计算出度为0的节点的PR总和，这些节点会将PR值随机分配给其他节点
			double deadSum = 0;  // 初始化出度为0节点的PR总和为0
			int deadCount = 0;  // 初始化出度为0的节点数量为0
			for (Vertex v : vertices) {
				if (outDegrees.get(v) == 0) {  // 如果这个顶点的出度为0（没有出边）
					deadSum += pr.get(v);  // 将其PR值累加到deadSum中
					deadCount++;  // 计数增加
				}
			}
			
			// 出度为0的节点将PR值均分给其他节点（随机跳转部分）
			double deadContribution = (deadCount > 0 && N > deadCount) ? deadSum / (N - deadCount) : 0;  // 计算这些节点的贡献，如果有的话
			
			// 计算每个节点的新的PR值
			for (Vertex v : vertices) {
				double sum = 0;  // 初始化前驱贡献的总和为0
				// 累加所有指向v的前驱节点的贡献
				for (Vertex pred : v.predecessors) {  // 遍历v的所有前驱节点
					int outDeg = outDegrees.get(pred);  // 获取前驱节点的出度
					if (outDeg > 0) {  // 如果前驱有出边
						sum += pr.get(pred) / outDeg;  // 累加前驱的PR值除以其出度
					}
				}
				// PR公式：(1-d)/N + d * (sum + deadContribution)
				// (1-d)/N 是随机跳转部分，d * (sum + deadContribution) 是链接跳转部分
				double newVal = (1 - d) / N + d * (sum + deadContribution);  // 计算新的PR值
				newPr.put(v, newVal);  // 将新PR值存入映射
			}
			
			pr = newPr;  // 更新pr为新一轮的PR值
		}
		
		return pr;  // 返回最终的PR值映射
	}
}
