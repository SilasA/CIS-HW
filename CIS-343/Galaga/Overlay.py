#!/usr/bin/env python3

import pygame

class Overlay(pygame.sprite.Sprite):
    def __init__(self, dank_mode):
        pygame.sprite.Sprite.__init__(self)
        if (dank_mode):
            self.image = pygame.Surface((1024, 50))
            self.font = pygame.font.Font("freesansbold.ttf", 36)
            self.rect = self.image.get_rect()
            self.render('Score: 0        Lives: 5        \'C\' to change player')
        else:
            self.image = pygame.Surface((1024, 20))
            self.font = pygame.font.Font("freesansbold.ttf", 18)
            self.rect = self.image.get_rect()
            self.render('Score: 0        Lives: 5        \'C\' to change color        Just don\'t press \'D\'')
        self.dank = dank_mode

    def render(self, text):
        self.text = self.font.render(text, True, (0, 0, 0))
        self.image.blit(self.text, self.rect)

    def draw(self, screen):
        screen.blit(self.text, (0, 0))

    def update(self, score, lives):
        if (self.dank):
            self.render("Score: " + str(score) + "        Lives: " + str(lives) +
                "        \'C\' to change player")
        else:
            self.render("Score: " + str(score) + "        Lives: " + str(lives) +
                "        \'C\' to change color        Just don\'t press \'D\'")

