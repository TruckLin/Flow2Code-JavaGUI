 i m p o r t   j a v a . u t i l . S c a n n e r ;   
 p u b l i c   c l a s s   F l o w C o d e { 
         p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s ) { 
 
                 i n t   m y N u m ; 
                 i n t   u s e r s G u e s s ; 
 
                 m y N u m   =   3 1 4 ; 
                 S y s t e m . o u t . p r i n t l n (   " W e l c o m e   t o   o u r   g a m e !   \ n   E n t e r   y o u r   f i r s t   g u e s s   : "   ) ; 
                 S c a n n e r   s c 1   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
                 u s e r s G u e s s   =   s c 1 . n e x t I n t ( ) ; 
                 s c 1 . c l o s e ( ) ; 
                 w h i l e (   u s e r s G u e s s   ! =   m y N u m   )   { 
 
                         S y s t e m . o u t . p r i n t l n (   " U n f o r t u n a t e !   T r y   a g a i n   :   "   ) ; 
                         S c a n n e r   s c 2   =   n e w   S c a n n e r ( S y s t e m . i n ) ; 
                         u s e r s G u e s s   =   s c 2 . n e x t I n t ( ) ; 
                         s c 2 . c l o s e ( ) ; 
                 } 
                 S y s t e m . o u t . p r i n t l n (   " C o n g r a t s   !   Y o u ' v e   g o t   i t   ! "   ) ; 
         } 
 }