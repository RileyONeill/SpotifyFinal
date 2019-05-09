import Cards
import Deck


class Player:
    def __init__(self, type, name):
        if type == 1:
            self.name = name
        if type == 0:
            self.name = "Dealer"

        self.score = 0
        self.hand = []

    def hit():
        hand.append(deck.pull_card())
        scores = score(hand, d_hand)
        print("You pulled the ", hand[len(hand) - 1])
