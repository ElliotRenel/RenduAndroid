# RenduAndroid

L'application ainsi que tous les bouttons présent marchais sur les machines testé.

Bouttons modification d'image :
  - Reset : réinitialize l'image à son état avant modifications
  - Gray Scale : Niveau de gris équivalent à toGray2
  - Keep Color : Garde une couleur donner en argument par le slider (dans un range de +-50°)
  - Contrast :
    * EC : Egaliseur Histograme Couleur
    * EG : Egaliseur Histograme Gris
    * LC : Histograme Linéair Couleur
    * LG : Histograme Linéair Gris

Bouttons changement d'image :
  - Gray Dune : image en niveaux de gris
  - Color Beach : image coloré fort contrast
  - Cute Doge : image coloré faible contrast
  - Wheel : roue de couleurs hsv

Voici toutes les fonctions présente dans MainActivity.java :
  - Fonctions annexes :
    * rgb_to_hsv(float red, float green, float blue, float[] hsv):
    Convertisseur RGB vers HSV (pixels mis dans l'argument hsv[])
    
    * hsv_to_rgb(float hsv[]):
    Convertisseur HSV vers RGB (pixels retourné sous forme de int)
    
    * bitmapToHistGray(Bitmap bmp, int[] hist):
    Calcule l'histogramme des pixels gris du Bitmap 'bmp'

    * bitmapToHistColor(Bitmap bmp, int[] hist, char color):
    Calcule l'histogramme d'une couleur spécifique des pixels de bmp

    * histToC(int[] hist, int[] C, int size):
    Calcule l'histogramme cumulé
  
  - Fonctions bouttons :

    * contrastLinearGrey(Bitmap bmp):
    Modifie le contraste d'une image en niveau de gris par extension linéaire

    * contrastLinearColor(Bitmap bmp):
    Modifie le contraste d'une image en couleur par extension linéaire

    * contrastEqualGrey(Bitmap bmp):
    Modifie le contraste d'une image en niveau de gris par égalisation d'histogramme

    * contrastEqualColor(Bitmap bmp):
    Modifie le contraste d'une image en couleur par égalisation d'histogramme

    * keepColor(Bitmap bmp, int color, int range):
    Modifie l'image en ne gardant que les pixels dont les couleurs HSV ont un H présent entre (color-range)° et (color+range)°

Machines testé :
  - Galaxy Nexus, API 28, Pie (machine virtuel)
  - Galaxy S9, API 27, Android 9 (smartphone personnel)
