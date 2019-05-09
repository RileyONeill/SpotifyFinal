import Cards
import Deck


class Player(Game):
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

class Game:



def start_game():

    name = input("Name: ")
    Player(1, name)


    deck = Deck.Deck()
    deck.shuffle()
    hand = [deck.pull_card(), deck.pull_card()]
    d_hand = [deck.pull_card(), deck.pull_card()]

    print("Your hand: ")
    for card in hand:
        print(card)

    print()
    print("Dealer shows: ", d_hand[1])

    scores = score(hand, d_hand)
    print("Your total: ", scores[0])

    player_move = input("What would you like to do? (Type 'help' for options):")

    if player_move == "help":
        print()
        print("Available moves: ")
        print("Hit")
        print("Stay")
        print("Double down")

    if player_move == "hit":

        print("Total:", scores[0])

    if player_move ==





def score(hand, d_hand):
    p_score = 0
    d_score = 0
    for card in hand:
        p_score += card.get_value()
    for card in d_hand:
        d_score += card.get_value()

    scores = [p_score, d_score]

    if p_score == 21:
        print("Blackjack!")
    if p_score > 21:
        print("You bust!")

    return scores


start_game()
