//Java implementation of a T Tree
import java.lang.Math;
import java.util.*;
public class TTree
{
TNode root=null; //initially tree is empty
int n; //no. of values in each node
int minAllow; //minimum allowable no. of entries
   public static void main(String args[]){
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter no. of values in each node");
    TTree ob=new TTree();
    ob.n=sc.nextInt();
    ob.minAllow=(int)Math.ceil((float)ob.n/2); //minimum allowed entries
    boolean t=true;
    while(t){
        System.out.println("Enter Choice:\n1: Insert\n2: Delete\n3: Display\n4: Exit");
        int a=sc.nextInt();
        switch(a)
        {
            case 1:System.out.println("Enter no. to be inserted"); 
            a=sc.nextInt();
            if(ob.root==null)
            {ob.root=new TNode(ob.n,null);
                ob.root.arr[0]=a;
                ob.root.m++;
            }else
            ob.insert(a,ob.root);
            break;
            case 2: System.out.println("Enter no. to be deleted");
            ob.delete(sc.nextInt(),ob.root);
            break;
            case 3: ob.display(ob.root);
            break;
            case 4:
            return ;
        }
}}
 public int balanceFactor(TNode temp) //Calculates balance factor at a particular node
 {
        return height(temp.lchild)-height(temp.rchild);
    }
public void display(TNode t) //Shows the inorder traversal of the tree
{
    if(t==null)
    return;
    if(t.lchild!=null)
    display(t.lchild);
    int i;
    System.out.println();
    for(i=0;i<t.m;i++)
    System.out.print(t.arr[i]+"\t");
    System.out.println();
    if(t.rchild!=null)
    display(t.rchild);
}
public void updateHeight(TNode temp) //To update the height of a particular node after balancing
{
    if(temp!=null)
     temp.height= 1+(int)Math.max(height(temp.lchild),height(temp.rchild));
    }
public void checkBalance(TNode a,int x,boolean ins) //called from insert if ins= true; called from delete if ins =false
{
    TNode temp=a;
int u;
while(temp!=null)
{
   updateHeight(temp); //updating heights of nodes in the root to leaf path
    u=balanceFactor(temp);
    if((int)Math.abs(u)>=2){
  TNode par=temp.parent;
  
if(u>1&&x<temp.lchild.min()) //RR rotation
{
    if(par==null){ // if temp is root
        root=rotateRight(temp);
        temp=root;
        temp.parent=null; //parent of root is null
    }
    else{
    if(par.rchild==temp) //if temp is the right child
    {par.rchild=rotateRight(temp);
        temp=par.rchild; //updating temp
    temp.parent=par;
}
else{ //if temp is the left child
par.lchild=rotateRight(temp);
temp=par.lchild;
temp.parent=par;
}}}
else{
    if(u>1&&x>temp.lchild.max()) //LR rotation
    {temp.lchild=rotateLeft(temp.lchild);
        temp.lchild.parent=temp;
     if(par==null){
        root=rotateRight(temp);
        temp=root;
        temp.parent=null;
    }else
    {
       if(par.rchild==temp)
    {par.rchild=rotateRight(temp);
        temp=par.rchild;
    temp.parent=par;
}
else{
par.lchild=rotateRight(temp);
temp=par.lchild;
temp.parent=par;
}}
//Special Case LR
if(temp.m<minAllow){
    int j=temp.m;
    int lim=minAllow-j;
    TNode temp0=temp.rchild;
    for(;j<minAllow;j++)
    {temp.arr[j]=temp0.min();
       temp0.arr= searchNDelete(temp0.min(),temp0.arr,temp0.m--);
}
temp.m+=lim;}}
else{
    if(u<1&& x<temp.rchild.min()) //RL rotation
    {
        temp.rchild=rotateRight(temp.rchild);
        temp.rchild.parent=temp;
        if(par==null){
        root=rotateLeft(temp);
        temp=root;
        temp.parent=null;
    }
    else
    {
        if(par.rchild==temp){
        par.rchild=rotateLeft(temp);
        temp=par.rchild;
        temp.parent=par;}
        else
        {
        par.lchild=rotateLeft(temp);
        temp=par.lchild;
        temp.parent=par;}     
      }  
//Special Case RL
if(temp.m<minAllow){
    int j=temp.m;
    int lim=minAllow-j;
    TNode temp0=temp.rchild;
    for(;j<minAllow;j++)
    {temp.arr[j]=temp0.min();
       temp0.arr= searchNDelete(temp0.min(),temp0.arr,temp0.m--);
}
temp.m+=lim;}
  }
  else{
      if(u<1&& x>temp.rchild.max()) //LL rotation
    {
        if(par==null){
        root=rotateRight(temp);
        temp=root;
        temp.parent=null;
    }else
         {
             if(par.rchild==temp) //if temp is the right child
    {par.rchild=rotateLeft(temp);
        temp=par.rchild;
    temp.parent=par;
}
else{
par.lchild=rotateLeft(temp); //if temp is the left child
temp=par.lchild;
temp.parent=par;
}}
}}}}
updateHeight(temp.parent);
if(ins)
break;}
   temp=temp.parent;
}
}
public TNode rotateRight(TNode t1) //Right AVL rotation
{
    TNode t2=t1.lchild;
    TNode t3=t2.rchild;
    t1.lchild=t3;
    t2.rchild=t1;
    t1.parent=t2;
    if(t3!=null)
    t3.parent=t1;
    //update the heights of nodes disturbed due to rotation
    updateHeight(t1);
    updateHeight(t2);
    return t2;
}
public TNode rotateLeft(TNode t1) //Left AVL rotation
{TNode t2=t1.rchild;
    TNode t3=t2.lchild;
    t2.lchild=t1;
    t1.rchild=t3;
        t1.parent=t2;
        if(t3!=null)
    t3.parent=t1;
    //update the heights of nodes disturbed due to rotation
    updateHeight(t1);
    updateHeight(t2);
    return t2;
}
public int[] insertIntoArray(int ar[],int val,int arraySize){
boolean t=false;
int i,temp=val;
for(i=0;i<arraySize;i++)
{
    if(t){
    ar[i]=ar[i]^temp;
temp=ar[i]^temp;
ar[i]=ar[i]^temp;
}
    else{
    if(val<=ar[i])
    {
    temp=ar[i];
    ar[i]=val;
    t=true;
}}
}
ar[i]=temp;
return ar;
}
public TNode search(int x,TNode temp){ //search bounding node
while(temp!=null)
{
    if(x<temp.min()){
    temp=temp.lchild;}
    else{
        if(x>temp.max()){
        temp=temp.rchild;}
        else //bounding node found
        {
            break;        }
    }
}
return temp;
}
public int height(TNode x) //calculates height at a particular node
{
        if(x==null)
        return 0;
        return x.height;
    }
public void insert(int x,TNode begin)
{TNode bNode=search(x,begin); //bounding node
    if(bNode!=null){ //bounding node found
if(bNode.m<n){
bNode.arr=insertIntoArray(bNode.arr,x,bNode.m);
bNode.m++;}
else
{
    int newVal=bNode.max(); //maximum value in node will be removed
    bNode.arr=insertIntoArray(bNode.arr,x,bNode.m-1); //original insert value is inserted in the current node
    insert(newVal,bNode); //removed value is inserted again
}}
  else
  {TNode temp=begin; //temp is used to traverse the tree
while(true)
{
    if(x<temp.min()){
        if(temp.lchild!=null)
    temp=temp.lchild;
else
{if(temp.m==n){
    temp.lchild=new TNode(n,temp); //create new left child
temp.lchild.arr[0]=x;
temp.lchild.m++; //update its array size
checkBalance(temp,x,true); //rebalancing because new node is inserted
}
else{
temp.arr=insertIntoArray(temp.arr,x,temp.m); //called because x will be inserted at the 0th index in the array
temp.m++;}
break;}}
    else{
        if(x>temp.max()){
              if(temp.rchild!=null)
        temp=temp.rchild;
    else
{
if(temp.m==n)
{    temp.rchild=new TNode(n,temp);
temp.rchild.arr[0]=x;
temp.rchild.m++;
checkBalance(temp,x,true); //rebalancing because new node is inserted
}
else{ //temp can acommodate x
temp.arr[temp.m]=x;
temp.m++;}
break;}
}
    }
}
}
}
//search the element and delete in the array of TNode
public int[] searchNDelete(int x, int ar[],int arsize){
    boolean t=false;
    for(int i=0;i<arsize-1;i++){
        if(t)
        {ar[i]=ar[i+1];
            continue;
        }
        if(ar[i]==x){
            t=true;
            i--;}
        }
        return ar;}
public void delete(int x,TNode r){
    TNode bNode=search(x,r);
    if(bNode==null){ //Value to be deleted not found
        System.out.println("Error! Value not found");
        return ;
    }
    else
    {
                   bNode.arr=searchNDelete(x,bNode.arr,bNode.m); //delete x in the bounding node
            bNode.m--; //update array size of bounding node
        if(bNode.m<minAllow)
        {
            if(bNode.lchild!=null && bNode.rchild!=null) //internal node
            {
                 TNode temp=bNode.rchild;
    while(temp.lchild!=null){
        temp=temp.lchild;
}
                bNode.arr[(bNode.m)++]=temp.min();
            delete(temp.min(),temp);}
                else
                {if(bNode.lchild!=null ^ bNode.rchild!=null) //half leaf node
                    {
                    if(bNode.lchild==null)
                    {
                        int l=bNode.m+bNode.rchild.m;
                        if(l<=n)
                        {for(int i=bNode.m;i<l;i++)
                            {
                                bNode.arr[i]=bNode.rchild.arr[i-bNode.m];
                            }
                            bNode.m=l;
                        bNode.rchild=null;
                       checkBalance(bNode,x,false);
                        }}
                    else
                    {
                        int i,l=bNode.m+bNode.lchild.m;
                        int tmp[]=new int[bNode.m];
                        if(l<=n)
                        {for(i=0;i<bNode.m;i++)
                            {
                                tmp[i]=bNode.arr[i];
                            }
                            for(i=0;i<l;i++)
                            {
                                if(i<bNode.lchild.m)
                                bNode.arr[i]=bNode.lchild.arr[i];
                                else
                                bNode.arr[i]=tmp[i-bNode.lchild.m];
                            }
                            bNode.m=l;
                        bNode.lchild=null;
                       checkBalance(bNode,x,false);
                    }}
                    }
                    else //leaf node
                    {
                        if(bNode.m==0)
                        {
                            TNode par=bNode.parent;
                            if(par.lchild==bNode) //if leaf node is the left child
                            par.lchild=null;
                            else
                            par.rchild=null;
                            checkBalance(par,x,false); //Rebalancing after removing node
                        }
                    }
                }
            }
        }
    }
}               
                        
                