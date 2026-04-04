package basis;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Vertex类表示有向图中的一个顶点，通常代表一个单词。
 * 在图论中，顶点是图的基本元素，这里用来存储单词及其关系。
 * 这个类包含了顶点的名称、入边（前驱）和出边（后继）信息，以及边的权重。
 */
public class Vertex {
	// 顶点的名称，比如一个单词如"hello"
	public String name;

	// 前驱集合：指向这个顶点的其他顶点，即入边的起点
	// 例如，如果有边 A -> B，则B的前驱包含A
	public HashSet<Vertex> predecessors;

	// 后继集合：从这个顶点指向的其他顶点，即出边的终点
	// 例如，如果有边 A -> B，则A的后继包含B
	public HashSet<Vertex> successors;

	// 权重映射：记录到每个后继顶点的边的权重（整数，表示边的强度或次数）
	// 例如，如果A到B有两条边，权重为2
	public HashMap<Vertex, Integer> weights;

	/**
	 * 默认构造函数，创建一个空的顶点。
	 * 名称设为null，集合和映射都初始化为空。
	 * 这通常用于临时创建对象，后续再设置名称。
	 */
	public Vertex() {
		this.name = null;  // 初始名称为空
		this.predecessors = new HashSet<>();  // 初始化前驱集合为空
		this.successors = new HashSet<>();    // 初始化后继集合为空
		this.weights = new HashMap<>();       // 初始化权重映射为空
	}

	/**
	 * 带参数的构造函数，创建一个有名称的顶点。
	 * @param name 顶点的名称，通常是一个单词字符串
	 * 集合和映射都初始化为空，等待后续添加边。
	 */
	public Vertex(String name) {
		this.name = name;  // 设置顶点名称
		this.predecessors = new HashSet<>();  // 初始化前驱集合为空
		this.successors = new HashSet<>();    // 初始化后继集合为空
		this.weights = new HashMap<>();       // 初始化权重映射为空
	}

	/**
	 * 判断两个顶点是否相等。
	 * 这里基于名称相等，因为在图中，相同名称的顶点被认为是同一个单词。
	 * @param v 要比较的另一个顶点
	 * @return 如果名称相同，返回true，否则false
	 */
	public boolean equals(Vertex v) {
		return this.name.equals(v.name);  // 比较名称字符串
	}

	/**
	 * 返回顶点的字符串表示形式。
	 * 通常用于打印或显示顶点信息。
	 * @return 顶点的名称字符串
	 */
	@Override
	public String toString() {
		return this.name;  // 直接返回名称
	}

	/**
	 * 返回顶点的哈希码，用于在集合或映射中快速查找。
	 * 基于名称的哈希码，因为名称唯一标识顶点。
	 * @return 名称的哈希码
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();  // 使用名称的哈希码
	}
}
