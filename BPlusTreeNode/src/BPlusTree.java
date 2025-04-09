import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// B+ Tree Node
class BPlusTreeNode {
    List<Integer> keys;
    List<BPlusTreeNode> children;
    BPlusTreeNode next; // Pointer to the next leaf node
    boolean isLeaf;

    BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }
}

// B+ Tree
public class BPlusTree {
    private BPlusTreeNode root;
    private int maxDegree;

    public BPlusTree(int maxDegree) {
        this.maxDegree = maxDegree;
        this.root = new BPlusTreeNode(true);
    }

    public void insert(int key) {
        BPlusTreeNode node = root;
        if (node.keys.size() == maxDegree - 1) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.children.add(node);
            splitChild(newRoot, 0, node);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(BPlusTreeNode node, int key) {
        if (node.isLeaf) {
            int pos = Collections.binarySearch(node.keys, key);
            if (pos < 0) {
                pos = -pos - 1;
            }
            node.keys.add(pos, key);
        } else {
            int pos = Collections.binarySearch(node.keys, key);
            if (pos < 0) {
                pos = -pos - 1;
            } else {
                pos++;
            }
            BPlusTreeNode child = node.children.get(pos);
            if (child.keys.size() == maxDegree - 1) {
                splitChild(node, pos, child);
                if (key > node.keys.get(pos)) {
                    pos++;
                }
            }
            insertNonFull(node.children.get(pos), key);
        }
    }

    private void splitChild(BPlusTreeNode parent, int pos, BPlusTreeNode child) {
        BPlusTreeNode newChild = new BPlusTreeNode(child.isLeaf);
        int mid = maxDegree / 2;

        parent.keys.add(pos, child.keys.get(mid));
        parent.children.add(pos + 1, newChild);

        newChild.keys.addAll(child.keys.subList(mid + 1, child.keys.size()));
        child.keys.subList(mid, child.keys.size()).clear();

        if (!child.isLeaf) {
            newChild.children.addAll(child.children.subList(mid + 1, child.children.size()));
            child.children.subList(mid + 1, child.children.size()).clear();
        } else {
            newChild.next = child.next;
            child.next = newChild;
        }
    }

    public void print() {
        print(root, 0);
    }

    private void print(BPlusTreeNode node, int level) {
        System.out.println("Level " + level + " " + node.keys);
        if (!node.isLeaf) {
            for (BPlusTreeNode child : node.children) {
                print(child, level + 1);
            }
        }
    }

    public static void main(String[] args) {
        BPlusTree bpt = new BPlusTree(4);

        bpt.insert(10);
        bpt.insert(20);
        bpt.insert(5);
        bpt.insert(6);
        bpt.insert(12);
        bpt.insert(30);
        bpt.insert(7);
        bpt.insert(17);

        bpt.print();
    }
}
