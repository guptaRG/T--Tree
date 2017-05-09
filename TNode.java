//T Tree Node
public class TNode
{
    int arr[];  //Array of children
    TNode parent;   //parent of current node
    TNode lchild;   //left child
    TNode rchild;   //right child
    int m;  //No. of children
    int height; //to check for balance
    public TNode(int n, TNode par){
    arr=new int[n];
    for(int i=0;i<n;i++)
    arr[i]=0;
    parent=par;
    lchild=null;
    rchild=null;
    m=0;
    height=1; // Due to this node
}
public int min()
{return arr[0];
}
public int max()
{
    return arr[m-1];
}
}