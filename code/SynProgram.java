import java.util.Scanner;
import java.util.EmptyStackException; //异常类型
import java.util.Stack;	//栈类的引用

class Analysis{
	Stack <Integer> statu = new Stack<>();	//建立状态栈[int]
	Stack <Character> signer = new Stack<>();	//建立符号栈[char]
	String input;//写入输入串
	String analysis;//分析动作
	int turn;//转向动作
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
		while(true) {
			String output=input.substring(index);
			char i=input.charAt(index);
			String action=table[statu.peek()+1][getOperationCol(i)];
			if(action.equals("$")) {
				System.out.println(action+"分析错误"+input+"不符合该SLR(1)文法！");
				break;
			}else if(action.equals("acc")) {
				System.out.println(action+"分析成功"+input+"符合该SLR(1)文法！");
				break;
			}else {
				String SorR=action.substring(0,1);//获取S或R
				if(SorR.equals("S")) {
					String number_action=action.substring(1);//获取S后面的值
					int value=Integer.parseInt(number_action);//将number_action转为integer
					statu.push(value);//将value压入状态栈
					signer.push(i);//将字符i压入符号栈
					index++;
					System.out.println(action+"分析动作完成！");
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
					System.out.println(action+"转向动作完成！使用"+useProduce+"完成归约");
				}
			}
		}
	}
	
}
public class SynProgram {
	public static void main(String args[]) {
		Analysis a = new Analysis();
		String input="d*(d+d+d-(d/d+d))#";
		a.set(input);
		a.analysis();
	}
}
