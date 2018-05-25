package E2;

public class Play implements P.Player {

	public int call (
		int[] mycard, int[] yourcard, 
		int[] mylog, int[] yourlog, 
		double mylastscore, double yourlastscore, 
		double mytotalscore, double yourtotalscore, 
		int count, long timeleft
	)
	{
		int submit = 0;
		java.util.Random r = new java.util.Random();
		while (true) {
			submit = r.nextInt(13) + 1;
			if (mycard[submit]==0) {
				return submit;
			}
		}

	}

}
