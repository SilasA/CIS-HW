#!/usr/bin/env python3

import pygame

class Ship(pygame.sprite.Sprite):
    def __init__(self, image):
        pygame.sprite.Sprite.__init__(self)
        self.image = image
        self.rect = self.image.get_rect()
        self.rect.x = 768 / 2
        self.rect.y = 768 - 100
        self.vector = [0,0]

    def update(self):
        self.rect.x += self.vector[0]
        self.rect.y += self.vector[1]

    def draw(self, screen):
        screen.blit(self.image, self.rect)

