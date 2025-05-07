
/* tests for L1 step 1 */

let x = 1;
  (x + 1);;

(let x = 1; (x + 1)) * (let x = 2; (x + 3))

let x = 1;
  let y = 2;
  (x + y)
;;

let x = 2;
let z = x+2;
let k = (let x = z+2; x*x);
k+k;;

let y = 1;
let b = (y > 0) && (y <= 20);
let z = (let z = 2*y;  z*z);
b || ~ (z < 0)
;;

/* tests for L1 step 2 */

let x = 1;
let f = fn y => { x + y };
(x + f (2));;

let x=1 ;			 
let f = fn y => { y+x } ;
let g = fn x => { x+f(x) };
g(2) 
;;

let f = fn g,z => { g (z) };
f (fn z => { z*2 }) (7)
;;

(fn z => { fn t => { z*t }}) (7) (7);;


(fn z,t => { z*t }) (7) (7);;

let x=1 ;			 
let f = fn y => {
           let k = x*2;
  	   y+x*k
        };
let g = fn x, u =>
           { u(x) + f(x) };
g  (f(3)) (f) 
;;

let comp = fn f, g => 
        { fn x =>
        { f (g (x)) }};
let inc = fn x => { x + 1};
let dup = fn x => { 2 * x};
let c2 = comp (inc) (dup);
c2 (99)
