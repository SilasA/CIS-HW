#!/usr/bin/env python3

import pygame

class Projectile(pygame.sprite.Sprite):
    def __init__(self, source, direction):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface((3, 8))
        pygame.draw.rect(self.image, (255, 0, 0), (0, 0, 3, 8))
        self.rect = self.image.get_rect()
        self.rect.x = source.rect.centerx
        if (direction < 0):
            self.rect.y = source.rect.y - 9
        else:
            self.rect.y = source.rect.bottom + 9
        self.vector = [ 0, 8 * direction ]

    def update(self, game, enemies, ship):
        if (self.rect.x < 1 or self.rect.x > 1020 or
            self.rect.y < 1 or self.rect.y > 760):
            game.shots.remove(self)
        hitObj = pygame.sprite.spritecollideany(self, enemies)
        if (hitObj and self.vector[1] < 0):
            game.shots.remove(self)
            hitObj.kill()
            game.score += game.ship_value
        if (pygame.sprite.collide_rect(self, ship) and self.vector[1] > 0):
            game.shots.remove(self)
            game.lives -= 1
            pygame.event.post(game.new_life_event)
        self.rect.x += self.vector[0]
        self.rect.y += self.vector[1]

