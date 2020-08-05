package test.java;

import com.main.java.Winnowing;
import org.junit.Test;

public class WinnowTest {
    @Test
    public void winnowTest() {
        String ctest = "#include<iostream>usingnamespacestd;template<classT>structBiNode{Tdata;BiNode<T>*lchild;BiNode<T>*rchild;};template<classT>classBiTree{public:BiTree();~BiTree();voidPreOrder(BiNode<T>*root);BiNode<T>*returnroot();private:BiNode<T>*root;BiNode<T>*Creat();voidRelease(BiNode<T>*root);};template<classT>BiTree<T>::BiTree(){this->root=Creat();}template<classT>BiNode<T>*BiTree<T>::Creat(){BiNode<T>*root;Tch;cin>>ch;if(ch=='#')root=NULL;else{root=newBiNode<T>;root->data=ch;root->lchild=Creat();root->rchild=Creat();}returnroot;}template<classT>BiTree<T>::~BiTree(){Release(root);}template<classT>voidBiTree<T>::Release(BiNode<T>*root){if(root!=NULL){Release(root->lchild);Release(root->rchild);deleteroot;}}template<classT>voidBiTree<T>::PreOrder(BiNode<T>*root){if(root==NULL)return;else{if(root->rchild==NULL&&root->lchild!=NULL)cout<<root->data<<\"\";PreOrder(root->lchild);PreOrder(root->rchild);}}template<classT>BiNode<T>*BiTree<T>::returnroot(){returnroot;}intmain(){BiTree<char>B;BiNode<char>*root;root=B.returnroot();B.PreOrder(root);cout<<endl;return0;}";
        String a = "A do run run run, a do run run";
        String b = "adoru";
        Winnowing winnow = new Winnowing();
//        System.out.println("指纹结果：");
        System.out.println(winnow.getHash(b));
//        System.out.println(winnow.winnowUsingCharacters(a));
//        System.out.println(winnow.winnowUsingCharacters(ctest));
    }
}
