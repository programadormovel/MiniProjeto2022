package br.edu.fieb.miniprojeto2022;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {
    /**
     * método de conversão de um objeto byte[] (array de bytes) em Bitmap
     * @param foto
     * @return
     */
    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }

    // https://www.it-swarm.dev/pt/android/compartilhar-imagem-e-texto-atraves-do-whatsapp-ou-facebook/1046274258/

    public static @Nullable
    Uri getUriFromFile(Context context, @Nullable File file) {
        if (file == null)
            return null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                return FileProvider.getUriForFile(context, "com.my.package.fileprovider", file);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return Uri.fromFile(file);
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = getUriFromFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static Uri getBytesUri(Context context, byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmapReduzido.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = getUriFromFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    // Converter Drawable para Bitmap
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap){
        byte[] imagemEnviada = null;

        if(bitmap != null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imagemEnviada = stream.toByteArray();
        }
        return imagemEnviada;
    }

    // métodos para validação de login
    public static boolean isContaPreenchida(String user) {
        boolean res = false;
        if (!TextUtils.isEmpty(user)
                && user.length() > 0) {
            res = true;
        }
        return res;
    }

    public static boolean isContaMaiorQueUm(String user) {
        boolean res = false;
        if (user.length() > 1) {
            res = true;
        }
        return res;
    }

    public static boolean isSenhaPreenchida(String senha) {
        boolean res = false;
        if (!TextUtils.isEmpty(senha)
                && senha.length() > 0) {
            res = true;
        }
        return res;
    }

    public static boolean isSenhaMaiorQueCinco(String senha) {
        boolean res = false;
        if (senha.length() > 5) {
            res = true;
        }
        return res;
    }

}
