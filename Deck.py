import Cards;
import random;


class Deck:
    def __init__(self):
        suits = ['Hearts', 'Diamonds', 'Spades', 'Clubs']
        ranks = ['Ace', '2', '3', '4', '5', '6', '7', '8', '9', '10', 'Jack', 'Queen', 'King']
        self.deck = [Cards.Card(rank, suit) for rank in ranks for suit in suits]

    def get_num(self):
        return len(self.deck)

    def shuffle(self):
        random.shuffle(self.deck)

    def read_deck(self):
        for card in self.deck:
            print(card)

    def pull_card(self):
        pulled_card = self.deck[0]
        del self.deck[0]
        return pulled_card

