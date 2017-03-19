<?php
$dir = __DIR__.'/assets/level/';
echo $dir;
$images = glob(__DIR__.'/assets/level/*');
foreach ($images as &$p){
    echo $p;
    echo 1;
}
echo 0;
?>