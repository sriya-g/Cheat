
public class CheatCPU {
	private double cheatChance;
	private double accuseChance;
	private Deck cpuCards;
	private String name;
	private int num;

	public CheatCPU(String name, int num) {
		this.cheatChance = Math.random();
		this.accuseChance = Math.random();
		this.cpuCards = new Deck(false);
		this.name = name;
		this.num = num;
	}
	public int numCardsInHand() {
		return cpuCards.getNumCards();
	}
	public double getCheatChance() {
		return cheatChance;
	}
	public void setCheatChance(double cheatChance) {
		this.cheatChance = cheatChance;
	}
	public Deck getCpuCards() {
		return cpuCards;
	}
	public void setCpuCards(Deck cpuCards) {
		this.cpuCards = cpuCards;
	}
	public void addcard(Card c) {
		cpuCards.getDeck().offer(c);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAccuseChance() {
		return accuseChance;
	}
	public void setAccuseChance(double accuseChance) {
		this.accuseChance = accuseChance;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
