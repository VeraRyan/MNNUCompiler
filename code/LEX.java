class lex{
	public char state='S';	//��ʼ״̬
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
						System.out.println(temp+",��ʮ����������");
					}else if(state==hexRealstate) {
						System.out.println(temp+",��ʮ������ʵ��");
					}else if(state==decstate) {
						System.out.println(temp+",��ʮ����������");
					}else if(state==decRealstate) {
						System.out.println(temp+",��ʮ����ʵ����");
					}else if(state==otcstate) {
						System.out.println(temp+",�ǰ˽���������");
					}else if(state==otcRealstate) {
						System.out.println(temp+",�ǰ˽���ʵ����");
					}
					System.out.println(ch+",�������");
				}else if(i==process.length()){
					if(state==hexstate) {
						System.out.println(temp+",��ʮ����������");
					}else if(state==hexRealstate) {
						System.out.println(temp+",��ʮ������ʵ��");
					}else if(state==decstate) {
						System.out.println(temp+",��ʮ����������");
					}else if(state==decRealstate) {
						System.out.println(temp+",��ʮ����ʵ����");
					}else if(state==otcstate) {
						System.out.println(temp+",�ǰ˽���������");
					}else if(state==otcRealstate) {
						System.out.println(temp+",�ǰ˽���ʵ����");
					}
					System.out.println("�ʷ���������");
				}
				temp="";
				state='S';
			}else if((ch>='0'&&ch<='9')||ch=='x'||ch=='X'||(ch>='a'&&ch<='f')||(ch>='A'&&ch<='F')||ch=='.'){
				if(ch=='0'&&state=='S') {
					if(process.charAt(i+1)=='x'||process.charAt(i+1)=='X') {
						temp+=ch;
						state='K';//��תʮ������
					}else if(process.charAt(i+1)=='.') {
						temp+=ch;
						state='P';//��ת����ʵ��
					}else if(process.charAt(i+1)=='+'||process.charAt(i+1)=='-'||process.charAt(i+1)=='*'||process.charAt(i+1)=='/') {
						temp+=ch;
						state=decstate;//��ת������̬
					}else if(process.charAt(i+1)>='0'&&process.charAt(i+1)<='7') {
						temp+=ch;
						state='U';//��ת�˽�����
					}
				}
				/**
				 * �����ǹ���ÿ����̬�ķ�֧˵��
				 * ������ʮ������״̬��ת
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
				 * ������ʮ������
				 * �����ǰ˽���
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
				 * �����ǰ˽���
				 * ������ʮ����
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
public class LEX {
	public static void main(String args[]) {
		String sentence="01546+0777.031-0x1234*0x21.34/0x988-1212+0*1.231/0.231";
		lex lex=new lex(sentence);
		System.out.println(sentence);
		lex.program();
	}
}
