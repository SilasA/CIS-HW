# !/usr/bin/env python3

# credit to www.kenney.nl for the sprites

import pygame
import os
import sys
import random

from Ship import Ship
from Overlay import Overlay
from Enemy import Enemy
from Overlay import Overlay
from Projectile import Projectile

class Game:
    def __init__(self, path):
        pygame.init()
        self.ship_imgs = [
            pygame.image.load(os.path.join(path, "player" + str(i) + ".png")) for i in range(1, 5)
        ]
        self.enemy_imgs = [
            pygame.image.load(os.path.join(path, "enemy" + str(i) + ".png")) for i in range(1, 5)
        ]
        self.bg_img = pygame.image.load(os.path.join(path, "bg.png"))

        pygame.key.set_repeat(50)
        self.dank_mode = path == 'dank'
        self.clock = pygame.time.Clock()
        self.screen = pygame.display.set_mode((1024, 768))
        self.shots = pygame.sprite.Group()
        self.index = 0
        self.ship = Ship(self.ship_imgs[self.index])
        self.enemies = pygame.sprite.Group()
        self.overlay = Overlay(self.dank_mode)
        self.new_life_event = pygame.event.Event(pygame.USEREVENT + 1)
        self.score = 0
        self.lives = 5
        self.ship_value = 10
        if (self.dank_mode):
            self.ship_value = 69

        width = 5 * 100
        for i in range(0, 4):
            for j in range(0, 6):
                e = Enemy(self.enemy_imgs[(i * j) % 4])
                e.rect.centerx = j * 100 + (1024 - width) / 2
                e.rect.centery = i * 100 + 100
                self.enemies.add(e)

    def run(self):
        self.done = False
        while not self.done:
            self.screen.fill((255, 255, 255))
            self.screen.blit(self.bg_img, [0,0])
            for event in pygame.event.get():
                if (event.type == self.new_life_event.type):
                    self.lives -= 1
                    if (self.lives > 0):
                        ship = Ship(self.ship_imgs[self.index])
                    else:
                        pygame.quit()
                        sys.exit(0)

                if (event.type == pygame.QUIT):
                    self.done = True

                if (event.type == pygame.KEYDOWN):                      
                    if (event.key == pygame.K_LEFT):
                        self.ship.vector[0] = -4
                        if (self.ship.rect.x <= 0):
                            self.ship.vector[0] = 0
                    if (event.key == pygame.K_RIGHT):
                        self.ship.vector[0] = 4
                        if (self.ship.rect.x >= 1024 - self.ship.rect.w):
                            self.ship.vector[0] = 0
                if (event.type == pygame.KEYUP):
                    if (event.key == pygame.K_SPACE):
                        p = Projectile(self.ship, -1)
                        self.shots.add(p)
                    if (event.key == pygame.K_LEFT):
                        self.ship.vector[0] = 0
                    if (event.key == pygame.K_RIGHT):
                        self.ship.vector[0] = 0
                    if (event.key == pygame.K_c):
                        self.index = (self.index + 1) % 4
                        self.ship.image = self.ship_imgs[self.index]
                    if (event.key == pygame.K_d and not self.dank_mode):
                        return "Trap card activated"

            self.ship.update()
            self.shots.update(self, self.enemies, self.ship)
            self.overlay.update(self.score, self.lives)
            self.enemies.update(game)
            # movement of enemy group
            boundHit = 'None'
            for e in self.enemies:
                if (e.rect.centerx - 50 <= 0):
                    boundHit = 'Left'
                    break
                elif (e.rect.centerx + 50 >= 1024):
                    boundHit = 'Right'

            speed = random.randrange(1, 3)
            for e in self.enemies:
                if (boundHit == 'Left'):
                    e.vector[0] = speed
                elif (boundHit == 'Right'):
                    e.vector[0] = -speed

            self.ship.draw(self.screen)
            self.shots.draw(self.screen)
            self.enemies.draw(self.screen)
            self.overlay.draw(self.screen)
            pygame.display.flip()
            self.clock.tick(60)

if __name__ == "__main__":
    game = Game("assets")
    if (game.run() == "Trap card activated"):
        game = Game("dank")
        game.run()

