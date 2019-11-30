import java.util.Scanner;
import java.util.EmptyStackException; //异常类型
import java.util.Stack;	//栈类的引用

class Analysis{
	Stack <Integer> statu = new Stack<>();	//建立状态栈[integer]
	Stack <Integer> StatuOutput = new Stack<>();//建立状态栈的倒推输出栈[integer]
	Stack <Character> signer = new Stack<>();	//建立符号栈[char]
	Stack <Character> SignerOutput = new Stack<>();	//建立符号栈的倒推输出栈[char]
	int [] LengthAboutStatuArray=new int[1000];//存放状态栈的明确长度
	int [] LengthAboutSignerArray=new int[1000];//存放符号栈的明确长度
	String input;//写入输入串
	String analysis;//分析动作
	int turn;//转向动作
	int [][] StatuArray=new int[1000][1000];//状态栈输出数组
	char [][] SignerArray=new char[1000][1000];//符号栈输出数组
	String [] outputArray=new String[1000];//储存剩余串
	char [] charArray=new char[1000];//当前读入符号
	String [] ActionArray=new String[1000];//当前的动作是什么
	String table[][]= {{"$","+","-","*","/","(",")","d","#","E","T","F","I"},//第0行
					   {"0","$","$","$","$","S6","$","S5","$","1","2","3","4"},
					   {"1","S7","S8","$","$","$","$","$","acc","$","$","$","$"},
					   {"2","R3","R3","S9","S10","$","R3","$","R3","$","$","$","$"},
					   {"3","R6","R6","R6","R6","R6","R6","R6","R6","$","$","$","$"},
					   {"4","R8","R8","R8","R8","R8","R8","R8","R8","$","$","$","$"},
					   {"5","R9","R9","R9","R9","R9","R9","R9","R9","$","$","$","$"},
					   {"6","$","$","$","$","S6","$","S5","$","11","2","3","4"},
					   {"7","$","$","$","$","S6","$","S5","$","$","14","3","4"},
					   {"8","$","$","$","$","S6","$","S5","$","$","15","3","4"},
					   {"9","$","$","$","$","S6","$","S5","$","$","$","12","4"},
					   {"10","$","$","$","$","S6","$","S5","$","$","$","13","4"},
					   {"11","S7","S8","$","$","$","S16","$","$","$","$","$","$"},
					   {"12","R4","R4","R4","R4","R4","R4","R4","R4","$","$","$","$"},
					   {"13","R5","R5","R5","R5","R5","R5","R5","R5","$","$","$","$"},
					   {"14","R1","R1","S9","S10","$","R1","$","R1","$","$","$","$"},
					   {"15","R2","R2","S9","S10","$","R2","$","R2","$","$","$","$"},
					   {"16","R7","R7","R7","R7","R7","R7","R7","R7","$","$","$","$"},
	                  };
	String produce[]= {//九个产生式
					   "E->E+T",
					   "E->E-T",
					   "E->T",
					   "T->T*F",
					   "T->T/F",
					   "T->F",
					   "F->(E)",
					   "F->I",
					   "I->d"
					  };
	public void set(String input) {
		this.input=input;//传入字符串
	}
	public int getOperationCol(char i) {
		int OperationCol=-1;
		for(int x=0;x<table[0].length;x++) {
			if(table[0][x].charAt(0)==i) {
				OperationCol=x;
			}
		}
		return OperationCol;
	}
	public void analysis() {
		statu.push(0);
		signer.push('#');
		int index=0;//字符串索引
		int count=0;//数组索引
		while(true) {
			{//状态栈传输规则：状态栈->状态倒推栈->状态输出数组->状态栈【回传】
				while(statu.empty()==false) {
					StatuOutput.push(statu.pop());//将状态栈的栈顶元素推出，然后输入给倒推栈
				}
				int j=0;//存放了二维单行数组的元素个数
				while(StatuOutput.empty()==false) {
					StatuArray[count][j]=StatuOutput.pop();//将倒推栈的栈顶输入给输出数组
					j++;
				}
				LengthAboutStatuArray[count]=j;//记录长度
				for(int x=0;x<j;x++) {
					statu.push(StatuArray[count][x]);//将输出数组的元素输入给状态栈
				}
			}
			{//符号栈传输规则：符号栈->符号倒推栈->符号输出数组->符号栈【回传】
				while(signer.empty()==false) {
					SignerOutput.push(signer.pop());//将符号栈的栈顶元素推出，然后输入给倒推栈
				}
				int j=0;//存放了二维单行数组的元素个数
				while(SignerOutput.empty()==false) {
					SignerArray[count][j]=SignerOutput.pop();//将倒推栈的栈顶输入给输出数组
					j++;
				}
				LengthAboutSignerArray[count]=j;//记录长度
				for(int x=0;x<j;x++) {
					signer.push(SignerArray[count][x]);//回传
				}
			}
			String output=input.substring(index);//剩余串
			outputArray[count]=output;
			//System.out.println("剩余串："+output);
			char i=input.charAt(index);
			charArray[count]=i;//当前读入符号
			String action=table[statu.peek()+1][getOperationCol(i)];
			//ActionArray[count]=action;//存入当前动作
			if(action.equals("$")) {
				ActionArray[count]=action+"分析错误"+input+"不符合该SLR(1)文法！";
				//System.out.println();
				show();
				break;
			}else if(action.equals("acc")) {
				ActionArray[count]=action+"分析成功"+input+"符合该SLR(1)文法！";
				//System.out.println();
				show();
				break;
			}else {
				String SorR=action.substring(0,1);//获取S或R
				if(SorR.equals("S")) {
					String number_action=action.substring(1);//获取S后面的值
					int value=Integer.parseInt(number_action);//将number_action转为integer
					statu.push(value);//将value压入状态栈
					signer.push(i);//将字符i压入符号栈
					index++;
					ActionArray[count]=action+"分析动作完成！";
					//System.out.println(action+"分析动作完成！");
				}else if(SorR.equals("R")) {
					String number_action=action.substring(1);//获取R后面的值
					int ReduceIndex=Integer.parseInt(number_action);//使用第几个产生式进行归约
					String useProduce=produce[ReduceIndex-1];//记录产生式
					char leftProduce=useProduce.charAt(0);//获取该产生式的左部符号，即归约符号
					String RightLength=useProduce.substring(3);//获取产生式的右部符号串
					int LengthAboutPopStack=RightLength.length();//获取右部符号串长度
					int PopStackValue[]=new int[LengthAboutPopStack];
					String PopSignerChar="";
					for(int j=0;j<LengthAboutPopStack;j++) {
						PopStackValue[j]=statu.pop();
						PopSignerChar+=signer.pop();
					}
					String returnValue=table[statu.peek()+1][getOperationCol(leftProduce)];//获取当前状态栈栈顶元素与归约非终结符的转向状态[String]
					int ValueOfReturn=Integer.parseInt(returnValue);
					signer.push(leftProduce);//将该左部符号压入符号栈
					statu.push(ValueOfReturn);
					ActionArray[count]=action+"转向动作完成！使用"+useProduce+"完成归约，跳转至"+ValueOfReturn;
					//System.out.println();
				}
			}
			count++;
		}
	}
	public void show() {
		int count=0;
		System.out.println("状态栈\t\t符号栈\t\t当前符号\t\t剩余串\t\t操作动作");
		while(outputArray[count]!=null) {
			/*状态栈输出*/
			String strAboutStatu="";
			for(int x=0;x<LengthAboutStatuArray[count];x++) {
				if(x<LengthAboutStatuArray[count]-1) {
					strAboutStatu=strAboutStatu+StatuArray[count][x]+",";
				}else {
					strAboutStatu=strAboutStatu+StatuArray[count][x];
				}
			}
			/*符号栈输出*/
			String strAboutSigner="";
			for(int x=0;x<LengthAboutSignerArray[count];x++) {
				if(x<LengthAboutSignerArray[count]-1) {
					strAboutSigner=strAboutSigner+SignerArray[count][x];
					//System.out.print(SignerArray[count][x]);
				}else {
					strAboutSigner=strAboutSigner+SignerArray[count][x];
					//System.out.print(SignerArray[count][x]+"\t\t");
				}
			}
			System.out.println(strAboutStatu+"\t\t"+strAboutSigner+"\t\t"+charArray[count]+"\t\t"+outputArray[count]+"\t\t"+ActionArray[count]);
			count++;
		}
	}
}
public class SynProgram {
	public static void main(String args[]) {
		Analysis a = new Analysis();
		String input="(d+d)#";
		a.set(input);
		a.analysis();
	}
}
