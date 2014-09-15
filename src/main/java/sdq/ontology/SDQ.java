package sdq.ontology;
import java.util.Date;

import jade.content.*;
public class SDQ implements Concept{

			/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			private int SDQID;
			
			public int getSDQID(){
				return SDQID;
			}
			
			public void setSDQID(int id){
				SDQID=id;
			}
			

			private String patientLName; 
			public String getpatientLName(){
				return patientLName; 
			}
			
			public void setpatientLName(String f){
				patientLName = f; 
			}
			
			
			private String patientFName; 
			public String getpatientFName(){
				return patientFName; 
			}
			
			public void setpatientFName(String f){
				patientFName = f; 
			}
			
			
			private int patientID;
			public int getpatientID(){
				return patientID;
			}
			
			
			private String patientGender;
			public String getPatientGender(){
				return patientGender;
			}
			public void setPatientGender(String g){
				patientGender = g; 
			}

			public void setPatientID(int i){
				patientID = i;
			}
	
			private Date date;
			public Date getDate(){
				return date; 
			}
			
			public void setDate(Date d){
				date = d;
			}
			
			private int age;
			public int getAge(){
				return age;
			}
			
			public void setAge(int a){
				age = a;
			}
			
			public int staffID;
			public int getStaffID(){
				return staffID;
			}
			
			public void setStaffID(int id){
				staffID =id;
			}
			int item1_Considerate;		//0 not true, 1 somewhat true, 2 certainly true
			public int getItem1_Considerate(){
				return item1_Considerate;
			}
			
			public void setItem1_Considerate(int i){
				item1_Considerate = i; 
			}
			
			int item2_Restless;
			public int getItem2_Restless(){
				return item2_Restless;
			}
			
			public void setItem2_Restless(int i){
				item2_Restless = i; 
			}
			int item3_Aches;
			public int getItem3_Aches(){
				return item3_Aches;
			}
			
			public void setItem3_Aches(int i){
				item3_Aches = i; 
			}
			int item4_Shares;
			public int getItem4_Shares(){
				return item4_Shares;
			}
			
			public void setItem4_Shares(int i){
				item4_Shares = i; 
			}
			int item5_Tempers;
			public int getItem5_Tempers(){
				return item5_Tempers;
			}
			
			public void setItem5_Tempers(int i){
				item5_Tempers = i; 
			}
			int item6_Solitary;
			public int getItem6_Solitary(){
				return item6_Solitary;
			}
			
			public void setItem6_Solitary(int i){
				item6_Solitary = i; 
			}
			int item7_Obedient;
			public int getItem7_Obedient(){
				return item7_Obedient;
			}
			
			public void setItem7_Obedient(int i){
				item7_Obedient = i; 
			}
			
			int item8_Worries;
			public int getItem8_Worries(){
				return item8_Worries;
			}
			
			public void setItem8_Worries(int i){
				item8_Worries = i; 
			}
			
			int item9_Helpful;
			public int getItem9_Helpful(){
				return item9_Helpful;
			}
			
			public void setItem9_Helpful(int i){
				item9_Helpful = i; 
			}
			int item10_Fidgety;
			public int getItem10_Fidgety(){
				return item10_Fidgety;
			}
			
			public void setItem10_Fidgety(int i){
				item10_Fidgety = i; 
			}
			
			int item11_Friend;
			public int getItem11_Friend(){
				return item11_Friend;
			}
			
			public void setItem11_Friend(int i){
				item11_Friend = i; 
			}
			int item12_Fights;
			public int getItem12_Fights(){
				return item12_Fights;
			}
			
			public void setItem12_Fights(int i){
				item12_Fights = i; 
			}
			int item13_Unhappy;
			public int getItem13_Unhappy(){
				return item13_Unhappy;
			}
			
			public void setItem13_Unhappy(int i){
				item13_Unhappy = i; 
			}
			int item14_Popular;
			public int getItem14_Popular(){
				return item14_Popular;
			}
			
			public void setItem14_Popular(int i){
				item14_Popular = i; 
			}
			int item15_Distractible;
			public int getItem15_Distractible(){
				return item15_Distractible;
			}
			
			public void setItem15_Distractible(int i){
				item15_Distractible = i; 
			}
			int item16_Clingy;
			public int getItem16_Clingy(){
				return item16_Clingy;
			}
			
			public void setItem16_Clingy(int i){
				item16_Clingy = i; 
			}
			int item17_Kind;
			public int getItem17_Kind(){
				return item17_Kind;
			}
			
			public void setItem17_Kind(int i){
				item17_Kind = i; 
			}
			int item18_lies;   //lies
			public int getItem18_lies(){
				return item18_lies;
			}
			
			public void setItem18_lies(int i){
				item18_lies = i; 
			}
			int item19_Victimized;
			public int getItem19_Victimized(){
				return item19_Victimized;
			}
			
			public void setItem19_Victimized(int i){
				item19_Victimized = i; 
			}
			int item20_Volunteers;
			public int getItem20_Volunteers(){
				return item20_Volunteers;
			}
			
			public void setItem20_Volunteers(int i){
				item20_Volunteers = i; 
			}
			int item21_Reflective;
			public int getItem21_Reflective(){
				return item21_Reflective;
			}
			
			public void setItem21_Reflective(int i){
				item21_Reflective = i; 
			}
			int item22_steals;   //steals
			public int getItem22_steals(){
				return item22_steals;
			}
			
			public void setItem22_steals(int i){
				item22_steals = i; 
			}
			int item23_Better_With_Adults;
			public int getItem23_Better_With_Adults(){
				return item23_Better_With_Adults;
			}
			
			public void setItem23_Better_With_Adults(int i){
				item23_Better_With_Adults = i; 
			}
			int item24_Fears;
			public int getItem24_Fears(){
				return item24_Fears;
			}
			
			public void setItem24_Fears(int i){
				item24_Fears = i; 
			}
			int item25_Attention;
			public int getItem25_Attention(){
				return item25_Attention;
			}
			
			public void setItem25_Attention(int i){
				item25_Attention = i; 
			}
			int impact1_Problem;	//0 not at all 1 only a little 2 quite a lot
			public int getImpact1_Problem(){
				return impact1_Problem;
			}
			
			public void setImpact1_Problem(int i){
				impact1_Problem = i;
			}
			int impact2_Duration; 	//3 a great deal, empty string if not answered.
			public int getImpact2_Duration(){
				return impact2_Duration;
			}
			
			public void setImpact2_Duration(int i){
				impact2_Duration = i;
			}
			int impact3_Distress;
			public int getImpact3_Distress(){
				return impact3_Distress;
			}
			
			public void setImpact3_Distress(int i){
				impact3_Distress = i;
			}
			
			int impact4_Home_Life;
			public int getImpact4_Home_Life(){
				return impact4_Home_Life;
			}
			
			public void setImpact4_Home_Life(int i){
				impact4_Home_Life = i;
			}
			int impact5_Peer;
			public int getImpact5_Peer(){
				return impact5_Peer;
			}
			
			public void setImpact5_Peer(int i){
				impact5_Peer = i;
			}
			int impact6_Learning;
			public int getImpact6_Learning(){
				return impact6_Learning;
			}
			
			public void setImpact6_Learning(int i){
				impact6_Learning = i;
			}
			int impact7_Leisure;
			public int getImpact7_Leisure(){
				return impact7_Leisure;
			}
			
			public void setImpact7_Leisure(int i){
				impact7_Leisure = i;
			}
			int impact8_Burden;
			public int getImpact8_Burden(){
				return impact8_Burden;
			}
			
			public void setImpact8_Burden(int i){
				impact8_Burden = i;
			}
			
}

