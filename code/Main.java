class lex{
	public char state='S';	//开始状态
	public String temp="";
	public String process="";
	public char hexstate='H';
	public char hexRealstate='V';
	public char decstate='D';
	public char decRealstate='T';
	public char otcstate='O';
	public char otcRealstate='W';
	lex(String sentence){
		process=sentence;
	}
	public void program() {
		char ch=' ';
		for(int i=0;i<process.length()+1;i++) {
			if(i<process.length()) {
				ch=process.charAt(i);
			}
			if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||i==process.length()) {
				if(ch=='+'||ch=='-'||ch=='*'||ch=='/') {
					if(state==hexstate) {
						System.out.println(temp+",是十六进制整数");
					}else if(state==hexRealstate) {
						System.out.println(temp+",是十六进制实数");
					}else if(state==decstate) {
						System.out.println(temp+",是十进制整数呢");
					}else if(state==decRealstate) {
						System.out.println(temp+",是十进制实数呢");
					}else if(state==otcstate) {
						System.out.println(temp+",是八进制整数呢");
					}else if(state==otcRealstate) {
						System.out.println(temp+",是八进制实数呢");
					}
					System.out.println(ch+",是运算符");
				}else if(i==process.length()){
					if(state==hexstate) {
						System.out.println(temp+",是十六进制整数");
					}else if(state==hexRealstate) {
						System.out.println(temp+",是十六进制实数");
					}else if(state==decstate) {
						System.out.println(temp+",是十进制整数呢");
					}else if(state==decRealstate) {
						System.out.println(temp+",是十进制实数呢");
					}else if(state==otcstate) {
						System.out.println(temp+",是八进制整数呢");
					}else if(state==otcRealstate) {
						System.out.println(temp+",是八进制实数呢");
					}
					System.out.println("词法分析结束");
				}
				temp="";
				state='S';
			}else if((ch>='0'&&ch<='9')||ch=='x'||ch=='X'||(ch>='a'&&ch<='f')||(ch>='A'&&ch<='F')||ch=='.'){
				if(ch=='0'&&state=='S') {
					if(process.charAt(i+1)=='x'||process.charAt(i+1)=='X') {
						temp+=ch;
						state='K';//跳转十六整数
					}else if(process.charAt(i+1)=='.') {
						temp+=ch;
						state='P';//跳转整型实数
					}else if(process.charAt(i+1)=='+'||process.charAt(i+1)=='-'||process.charAt(i+1)=='*'||process.charAt(i+1)=='/') {
						temp+=ch;
						state=decstate;//跳转整型终态
					}else if(process.charAt(i+1)>='0'&&process.charAt(i+1)<='7') {
						temp+=ch;
						state='U';//跳转八进整数
					}
				}
				/**
				 * 上面是关于每个形态的分支说明
				 * 下面是十六进制状态跳转
				 * **/
				 else if((ch=='x'||ch=='X')&&state=='K') {
					temp+=ch;
					state='M';
				}else if(((ch>='0'&&ch<='9')||(ch>='a'&&ch<='f')||(ch>='A'&&ch<='F'))&&state=='M'){
					temp+=ch;
					state=hexstate;
				}else if(((ch>='0'&&ch<='9')||(ch>='a'&&ch<='f')||(ch>='A'&&ch<='F'))&&state==hexstate){
					temp+=ch;
					state=hexstate;
				}else if(ch=='.'&&state==hexstate) {
					temp+=ch;
					state='Q';
				}else if(((ch>='0'&&ch<='9')||(ch>='a'&&ch<='f')||(ch>='A'&&ch<='F'))&&state=='Q') {
					temp+=ch;
					state=hexRealstate;
				}else if(((ch>='0'&&ch<='9')||(ch>='a'&&ch<='f')||(ch>='A'&&ch<='F'))&&state==hexRealstate){
					temp+=ch;
					state=hexRealstate;
				}
				/**
				 * 上面是十六进制
				 * 下面是八进制
				**/
				 else if((ch>='0'&&ch<='7')&&state=='U') {
					temp+=ch;
					state=otcstate;
				}else if((ch>='0'&&ch<='7')&&state==otcstate) {
					temp+=ch;
					state=otcstate;
				}else if(ch=='.'&&state==otcstate) {
					temp+=ch;
					state='E';
				}else if((ch>='0'&&ch<='7')&&state=='E'){
					temp+=ch;
					state=otcRealstate;
				}else if((ch>='0'&&ch<='7')&&state==otcRealstate) {
					temp+=ch;
					state=otcRealstate;
				}
				/**
				 * 上面是八进制
				 * 下面是十进制
				 * **/
				else if((ch>='1'&&ch<='9')&&state=='S') {
					temp+=ch;
					state=decstate;
				}else if((ch>='0'&&ch<='9')&&state==decstate) {
					temp+=ch;
					state=decstate;
				}else if(ch=='.'&&state=='P') {
					temp+=ch;
					state='P';
				}else if(ch=='.'&&state==decstate) {
					temp+=ch;
					state='P';
				}else if((ch>='0'&&ch<='9')&&state=='P') {
					temp+=ch;
					state=decRealstate;
				}else if((ch>='0'&&ch<='9')&&state==decRealstate) {
					temp+=ch;
					state=decRealstate;
				}
			}
		}
	}
}
public class Main {
	public static void main(String args[]) {
		String sentence="01546+0777.031-0x1234*0x21.34/0x988-1212+0*1.231/0.231";
		lex lex=new lex(sentence);
		System.out.println(sentence);
		lex.program();
	}
}
