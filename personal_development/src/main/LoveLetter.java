package main;

import java.util.ArrayList;

import Cards.Card;

public class LoveLetter {
	public static String red = "\u001b[00;31m";
	public static String green = "\u001b[00;32m";
	public static String yellow = "\u001b[00;33m";
	public static String purple = "\u001b[00;34m";
	public static String pink = "\u001b[00;35m";
	public static String cyan = "\u001b[00;36m";
	public static String end = "\u001b[00m";

	static java.util.Scanner scanner = new java.util.Scanner(System.in);

	public static void main(String[] args) {
		//new LoveLetterTitle().displayTitle();
		ArrayList<Hand> players = new ArrayList<Hand>();
		Hand hand = new Hand("Player");
		int playerNum;
		System.out.println("対戦人数を指定してください(1-3)");
		while (true) {
			String input = scanner.nextLine();

			if (input.matches("[1-3]")) {
				playerNum = Integer.parseInt(input);
				break;
			} else {
				System.out.println(red + "不正な入力値です。もう一度入力してください" + end);
				continue;
			}
		}

		players.add(hand);
		for (int i = 1; i <= playerNum; i++) {
			players.add(new Com(i));
		}

		for (Hand player : players) {
			player.setPlayers(players);
		}

		System.out.println("【プレイヤー一覧】");
		for (int i = 0; i <= playerNum; i++) {
			slow();
			System.out.println(cyan + players.get(i).getName() + end);
		}
		slow();
		System.out.println();

		int maxPoint = 0;
		int gameCount = 1;
		while (maxPoint < 3) {
			//初期状態に戻す
			for (Hand player : players) {
				player.reset();
			}
			System.out.println();

			System.out.println("⭐️" + gameCount + "ラウンド目");
			if (gameCount > 1) {
				System.out.println("【途中経過】");
				for (int i = 0; i <= playerNum; i++) {
					slow();
					System.out.println(cyan + players.get(i).getName() + ": " + players.get(i).point + "ポイント" + end);
				}
			}
			System.out.println();
			Deck deck = new Deck(players);
			//　カード廃棄時に使用するフラグ
			boolean rFlag = true;
			//１ターン目か否かを判定するフラグ
			boolean firstTurnFlag = true;
			//誰から始まるかを決める
			int k = 0;

			deck.shuffle();
			hand.drawCard(deck);
			for (int i = 1; i <= playerNum; i++) {
				players.get(i).drawCard(deck);
			}
			slow();
			hand.showHand();
			while (deck.getDeckSize() > 0) {
				//一人以外全員負けたらループを抜ける
				if (finFlag(players)) {
					break;
				}
				if (firstTurnFlag) {
					k = new java.util.Random().nextInt(players.size());
				} else {
					k = 0;
				}
				for (Hand player: players) {
					if (finFlag(players)) {
						break;
					}
					if (player.lFlag) {
						continue;
					}
					if (deck.getDeckSize() == 0) {
						break;
					}
					//侍女の効果をリセット
					player.efFlag = true;
					//記憶の確認
					if (player instanceof Com) {
						((Com) player).memoryCheck();
					}
					System.out.println();
					slow();
					System.out.println(cyan + player.getName() + "のターン" + end);
					slow();
					System.out.println("カードを引きます");
					player.drawCard(deck);
					if (!(player instanceof Com)) {
						slow();
						hand.showHand();
					}
					for (Card card : player.hands) {
						card.effect(player, 0);
					}
					if (player.lFlag) {
						slow();
						System.out.println();
						continue;
					}
					if (!(player instanceof Com)) {
						rFlag = true;
						while (rFlag) {
							slow();
							System.out.println("どのカードを捨てますか？(1:元々の手札　2:引いた手札)");
							String input = scanner.nextLine();
							rFlag = hand.remove(input);
						}
					} else {
						((Com) player).remove();
					}
					if (hand.lFlag) {
						continue;
					}
					if (player instanceof Com) {
						slow();
						slow();
						slow();
						slow();
						slow();
					}
				}
				firstTurnFlag = false;
			}
			System.out.println();
			System.out.println("ラウンド終了です");
			System.out.println();
			//以下終了処理
			System.out.println();
			//最後まで残ったプレイヤーの最終手札表示
			for (Hand player : players) {
				if (!player.lFlag) {
					slow();
					player.showHand();
				}
			}
			int max = 0;
			for (Hand player : players) {
				if (player.lFlag) {
					continue;
				}
				if (player.hands.get(0).getRank() > max) {
					max = player.hands.get(0).getRank();
				}
			}
			for (Hand player : players) {
				if (player.lFlag) {
					continue;
				}
				if (max == player.hands.get(0).getRank()) {
					player.flag = true;
				}
			}
			for (Hand player : players) {
				if (player.flag && !player.lFlag) {
					slow();
					System.out.println(yellow + player.getName() + "の勝利" + end);
					player.point++;
					if (player.hands.get(0).getRank() == 8) {
						player.point++;
					}
				}
			}
			//最大得点を調査
			for (Hand player : players) {
				if (player.point > maxPoint) {
					maxPoint = player.point;
				}
			}
			//ゲームカウントを進める
			gameCount++;
		}
		for (Hand player : players) {
			if (player.point >= 3) {
				System.out.println();
				System.out.println("【最終結果】");
				slow();
				System.out.println(yellow + player.getName() + "の勝利" + end);
			}
		}
		System.out.println();
		for (Hand player : players) {
			slow();
			System.out.println(cyan + player.getName() + ": " + player.point + "ポイント" + end);
		}
	}

	public static void slow() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

 
	
	public static boolean finFlag(ArrayList<Hand> players) {
		int finFlag = 0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).lFlag) {
				finFlag++;
			}
		}
		if (finFlag <= 1) {
			return true;
		} else {
			return false;
		}
	}
}
