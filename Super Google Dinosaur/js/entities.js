function char() {
  this.x = 50;
  this.y = 200;
  this.x_size = 96;
  this.y_size = 112;
  this.onAir = false;

  this.acc = 0; // acceleration
  this.opaque = true;  // can get "collisioned"

  this.img = new Image();
  this.img.src = 'img/char.png';

  this.jump = function() {
    this.onAir = true;
    this.acc = 17;
    points += 10;
  };

  this.draw = function() {
    ctx.drawImage(this.img, 0, 0, x_size, y_size, this.x, this.y, x_size/1.5, y_size/1.5);
  };

  this.update = function() {
    if (this.onAir) {
      this.y = this.y - this.acc;
      this.acc -= 1;
    }
    if (this.y >= 200) this.onAir = false;
    if (this.y < 160) this.opaque = false;
    else this.opaque = true;
  };
}

function pedobear() {
  this.x = 700;
  this.y = 220;

  this.x_size = 311;
  this.y_size = 240;

  this.acc = level;

  this.img = new Image();
  this.img.src = 'img/pedobear.png';

  this.tick = function() {
    ctx.drawImage(this.img, 0, 0, this.x_size, this.y_size, this.x, this.y, this.x_size/4, this.y_size/4);
    this.acc = level;
    if (this.x > -70) this.x = this.x - this.acc;
    else {
      if (Math.random() < 0.08) this.x = 700;  // randown reappearing
    }
  };
}

function collide(char, pedobear) {
  return  ((pedobear.x < char.x + char.x_size/2 - 10 && pedobear.x > char.x) ||
          (pedobear.x + pedobear.x_size/4 < char.x + char.x_size/2 - 10 && pedobear.x + pedobear.x_size/4 > char.x)) &&
          char.opaque;
}

function ground() {
  this.x = 0;
  this.y = 280;

  this.x_size = 1400;
  this.y_size = 90;

  this.img = new Image();
  this.img.src = 'img./ground.png';

  this.x2 = 0;
  this.img2 = new Image();
  this.img2.src = 'img/ground2.png';

  this.img3 = new Image();
  this.img3.src = 'img/ground3.png';

  this.draw = function() {
    ctx.drawImage(this.img3, 0, 0);
    ctx.drawImage(this.img2, this.x2, 0, this.x_size, 300, 0, 0, this.x_size, 300);
    ctx.drawImage(this.img, this.x, 0, this.x_size, this.y_size, 0, this.y, this.x_size, this.y_size);
  };

  this.update = function() {
    if(this.x > this.x_size/2) {
      this.x = 0;
    } else {
      this.x += 10;
    }

    if(this.x2 > this.x_size/2) {
      this.x2 = 0;
    } else {
      this.x2 += 2;
    }
  };
}
