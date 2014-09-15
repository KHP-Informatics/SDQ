package sdq.ontology;

import jade.content.onto.*;
import jade.content.schema.*;

public class SDQOntology extends Ontology implements SDQVocabulary {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		// ----------> The name identifying this ontology
		public static final String ONTOLOGY_NAME = "SDQ-Ontology";

		// ----------> The singleton instance of this ontology
		private static Ontology instance = new SDQOntology();

		// ----------> Method to access the singleton ontology object
		public static Ontology getInstance() { return instance; }

		// Private constructor
		private SDQOntology() {

		super(ONTOLOGY_NAME, BasicOntology.getInstance());

		try {

			// add Schemas
			
			add(new ConceptSchema(INFORMANT),Informant.class);
			add(new ConceptSchema(SDQ),SDQ.class);
			
		// ------- Add Concepts
			
			//Informant
			ConceptSchema cs = (ConceptSchema) getSchema(INFORMANT);
			cs.add(INFORMANT_FNAME,  (PrimitiveSchema) getSchema(BasicOntology.STRING),
										ObjectSchema.OPTIONAL);

			cs.add(INFORMANT_LNAME,  (PrimitiveSchema) getSchema(BasicOntology.STRING),
										ObjectSchema.OPTIONAL);

			
			cs.add(INFORMANT_TYPE,  (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);

			cs.add(INFORMANT_RELATIONSHIP,  (PrimitiveSchema) getSchema(BasicOntology.STRING),
										ObjectSchema.OPTIONAL);
			
			cs.add(INFORMANT_EMAIL,  (PrimitiveSchema) getSchema(BasicOntology.STRING),
										ObjectSchema.OPTIONAL);
			

			cs.add(INFORMANT_PHONENUMBER, (PrimitiveSchema) getSchema(BasicOntology.STRING),
										ObjectSchema.OPTIONAL);
			
			
			
			//SDQ
			cs = (ConceptSchema) getSchema(SDQ);
						
			cs.add(SDQ_SDQID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.MANDATORY);
			
			cs.add(SDQ_PATIENTID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.MANDATORY);
			
		
			cs.add(SDQ_PATIENTFNAME, (PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			
			cs.add(SDQ_PATIENTLNAME, (PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_DATE, (PrimitiveSchema) getSchema(BasicOntology.DATE),
					ObjectSchema.OPTIONAL);

			cs.add(SDQ_AGE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			
			cs.add(SDQ_PATIENTGENDER,  (PrimitiveSchema) getSchema(BasicOntology.STRING),
						ObjectSchema.OPTIONAL);
			
			cs.add(SDQ_STAFFID, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
				
			
			
			cs.add(SDQ_ITEM1CONSIDERATE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_ITEM2RESTLESS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_ITEM3_ACHES, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_ITEM4_SHARES, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_ITEM5_TEMPERS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM6_SOLITARY, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM7_OBEDIENT, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM8_WORRIES, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_ITEM9_HELPFUL, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM10_FIDGETY, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM11_FRIEND, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM12_FIGHTS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM13_UNHAPPY, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM14_POPULAR, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM15_DISTRACTIBEL, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			

			cs.add(SDQ_ITEM16_CLINGY, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM17_KIND, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM18_LIES, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM19_VICTIMIZED, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM20_VOLUNTEERS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM21_REFLECTIVE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM22_STEALS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM23_BETTERWITHADULTS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM24_FEARS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_ITEM25_ATTENTION, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			
			
			cs.add(SDQ_IMPACT1_PROBLEM, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			
			cs.add(SDQ_IMPACT2_DURATION, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_IMPACT3_DISTRESS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_IMPACT4_HOMELIFE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_IMPACT5_PEER, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_IMPACT6_LEARNING, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_IMPACT7_LEISURE, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(SDQ_IMPACT8_BURDEN, (PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			
			
			
			AgentActionSchema as = new AgentActionSchema(TRANSFERREPORT);
			add(as,TransferReport.class);
			as.add(TRANSFERREPORT_INFORMANT, (ConceptSchema) getSchema(INFORMANT));
			as.add(TRANSFERREPORT_SDQ,(ConceptSchema) getSchema(SDQ));
				
		}
		catch (Exception oe) {
		oe.printStackTrace();
		}
		}
}// SDQOntology



