package test.java;

import com.main.java.DelComments;
import com.main.java.Winnowing;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class WinnowTest {
    @Test
    public void winnowTest() {
        String ctest1 = "#include<iostream>usingnamespacestd;template<classT>structBiNode{Tdata;BiNode<T>*lchild;BiNode<T>*rchild;};template<classT>classBiTree{public:BiTree();~BiTree();voidPreOrder(BiNode<T>*root);BiNode<T>*returnroot();private:BiNode<T>*root;BiNode<T>*Creat();voidRelease(BiNode<T>*root);};template<classT>BiTree<T>::BiTree(){this->root=Creat();}template<classT>BiNode<T>*BiTree<T>::Creat(){BiNode<T>*root;Tch;cin>>ch;if(ch=='#')root=NULL;else{root=newBiNode<T>;root->data=ch;root->lchild=Creat();root->rchild=Creat();}returnroot;}template<classT>BiTree<T>::~BiTree(){Release(root);}template<classT>voidBiTree<T>::Release(BiNode<T>*root){if(root!=NULL){Release(root->lchild);Release(root->rchild);deleteroot;}}template<classT>voidBiTree<T>::PreOrder(BiNode<T>*root){if(root==NULL)return;else{if(root->rchild==NULL&&root->lchild!=NULL)cout<<root->data<<\"\";PreOrder(root->lchild);PreOrder(root->rchild);}}template<classT>BiNode<T>*BiTree<T>::returnroot(){returnroot;}intmain(){BiTree<char>B;BiNode<char>*root;root=B.returnroot();B.PreOrder(root);cout<<endl;return0;}";
        String ctest2 = "#include<iostream>usingnamespacestd;template<classT>structBiNode{Tdata;BiNode<T>*lchild;BiNode<T>*rchild;};template<classT>classBiTree{public:BiTree();~BiTree();voidPreOrder(BiNode<T>*root);BiNode<T>*returnroot();private:BiNode<T>*root;BiNode<T>*Creat();voidRelease(BiNode<T>*root);};template<classT>BiTree<T>::BiTree(){this->root=Creat();}template<classT>BiNode<T>*BiTree<T>::Creat(){BiNode<T>*root;Tch;cin>>ch;if(ch=='#')root=NULL;else{root=newBiNode<T>;root->data=ch;root->lchild=Creat();root->rchild=Creat();}returnroot;}template<classT>BiTree<T>::~BiTree(){Release(root);}template<classT>voidBiTree<T>::Release(BiNode<T>*root){if(root!=NULL){Release(root->lchild);Release(root->rchild);deleteroot;}}template<classT>voidBiTree<T>::PreOrder(BiNode<T>*root){if(root==NULL)return;else{if(root->rchild==NULL&&root->lchild!=NULL)cout<<root->data<<\"\";PreOrder(root->lchild);PreOrder(root->rchild);}}template<classT>BiNode<T>*BiTree<T>::returnroot(){returnroot;}intmain(){inta;intb;BiTree<char>B;BiNode<char>*root;root=B.returnroot();B.PreOrder(root);cout<<endl;return0;}";
//        String ctest3 = "#include<iostream>usingnamespacestd;constintM=20;constintN=20;voidIntersection(intA[],intB[],intI[]);voidUnion(intA[],intB[],intU[]);intmain(){inti,number1,number2;intA[M];intB[N];intI[M];intU[M];charoperation;cin>>operation;while(operation!='E'){switch(operation){case'A':{cin>>number1;A[0]=number1;for(i=1;i<number1+1;i++){cin>>A[i];}break;}case'B':{cin>>number2;B[0]=number2;for(i=1;i<number2+1;i++){cin>>B[i];}break;}case'I':{Intersection(&A[0],&B[0],&I[0]);for(i=1;i<I[0]+1;i++){cout<<I[i]<<endl;}break;}case'U':{Union(&A[0],&B[0],&U[0]);for(i=1;i<U[0]+1;i++){cout<<U[i]<<endl;}break;}default:{break;}}cin>>operation;}return0;}voidIntersection(intA[],intB[],intI[]){inti;intj;intk=0;for(i=1;i<A[0]+1;i++){for(j=1;j<B[0]+1;j++){if(A[i]==B[j]){k++;I[k]=A[i];j=B[0]+1;}}}I[0]=k;}voidUnion(intA[],intB[],intU[]){inti;intj;intt;intk=0;intcount;for(i=0;i<A[0]+1;i++){U[i]=A[i];}count=A[0];for(i=1;i<B[0]+1;i++){t=0;for(j=1;j<A[0]+1;j++){if(B[i]!=A[j]){t++;}else{j=A[0]+1;}}if(t==A[0]){count++;U[count]=B[i];}}U[0]=count;}";
        String a = "A do run run run, a do run run r";
//        System.out.println(winnow.winnowUsingCharacters(a));

        String file1 = "D:/代码相似性检测源码等/课程设计---c++代码相似度计算/课程设计---c++代码相似度计算/代码相似度/1.txt";
        String file2 = "D:/代码相似性检测源码等/课程设计---c++代码相似度计算/课程设计---c++代码相似度计算/代码相似度/3.txt";
        // 预处理：删除注释、空格、换行
        ArrayList<String> codeArray = DelComments.clearCommentandBlank(file1);
        ArrayList<String> codeArray2 = DelComments.clearCommentandBlank(file2);
        Winnowing winnow = new Winnowing();
        Set<Integer> setfile = new TreeSet<>();
        for (String s : codeArray) {
            System.out.println("分项：" + winnow.winnowUsingCharacters(s));
            setfile.addAll(winnow.winnowUsingCharacters(s));
        }
        System.out.println("setfile" + setfile);
        Set<Integer> setctest1 = winnow.winnowUsingCharacters(ctest1);
        Set<Integer> setctest3 = winnow.winnowUsingCharacters(ctest2);
//        System.out.println("指纹结果1：");
//        System.out.println(setctest1);
//        System.out.println("指纹结果2：");
//        System.out.println(setctest3);
//        String winsim = winnow.WinSimCalculator(setctest1, setctest3);
//        System.out.println("指纹提取公式相似度：" + winsim);
    }
}
