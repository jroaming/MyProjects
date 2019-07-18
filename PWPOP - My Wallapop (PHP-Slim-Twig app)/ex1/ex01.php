<?php

if ($argc !== 2) {
    echo "Usage: exercise1.php [number]";
    exit(1);
}

$number = (int)$argv[1];

if ($number === 0) {
    // Invalid Number
}
else {
    for ($i = 0; $i <= 10; $i++) {
        echo $i * $number . "\n";
    }
}