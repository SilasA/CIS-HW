#!/usr/bin/env python3

import pygame
import random

from Projectile import Projectile

class Enemy(pygame.sprite.Sprite):
    def __init__(self, image):
        pygame.sprite.Sprite.__init__(self)
        self.image = image
        self.rect = self.image.get_rect()
        self.vector = [-2, 0]

    def update(self, game):
        if (random.randrange(1000) == 50):
            s = Projectile(self, 1)
            game.shots.add(s)

        self.rect.x += self.vector[0]
        self.rect.y += self.vector[1]

    def draw(self, screen):
        screen.blit(self.image, self.rect)

