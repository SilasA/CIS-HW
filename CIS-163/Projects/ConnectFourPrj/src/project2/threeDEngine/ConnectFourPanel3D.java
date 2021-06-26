/***********************************************************************
 * @author Silas Agnew
 * @version February 19, 2018
 **********************************************************************/

package project2.threeDEngine;

import studios.vanish.engine.Color;
import studios.vanish.engine.FillMode;
import studios.vanish.engine.GraphicsUnit;
import studios.vanish.engine.Index;
import studios.vanish.engine.Object3D;
import studios.vanish.engine.Point;
import studios.vanish.engine.Size;
import studios.vanish.engine.Vertex;
import studios.vanish.engine.Window;

public class ConnectFourPanel3D
{
    public static int WIN_H = 720;
    public static int WIN_W = 1280;
    public static int FPS   = 60;

    private Object3D board;
    private Window   window;

    private int boardSize = -1;

    public ConnectFourPanel3D(int size)
    {
        board = new Object3D();
        window = new Window("Connect 4 3D", new Size(WIN_W, WIN_H), true);
        boardSize = size;
        Initialize();
    }

    public void Initialize()
    {
        window.OnPaint.Add(this, "Render");
        window.Initialize(FPS);
        window.Initialize3D(new Size(WIN_W, WIN_H), 1024, FillMode.Solid, false, true);
        window.Show();

        int vertices = (boardSize + 1) * (boardSize + 1);
        int indices = boardSize * boardSize;

        Vertex[] v = new Vertex[vertices];
        int idx = 0;
        for (double i = -(boardSize / 2.0); i <= boardSize / 2.0; i++)
        {
            for (double j = -(boardSize / 2.0); j <= boardSize / 2.0; j++)
            {
                v[idx] = new Vertex(i, 0, j);
                idx++;
            }
        }
        board.Vertices = v;

        Index[] in = new Index[indices];
        for (int i = 0; i < boardSize; i++)
        {
            in[i] = new Index(i, i + 1, i + boardSize + 2, i + boardSize + 1);
        }
        board.Indices = in;

        Color[] colors = new Color[indices];
        Color start = new Color(Color.Red, 255);
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                if (j % 2 == 0)
                {
                    colors[i * boardSize + j] = new Color(start, 255);
                }
                else
                {
                    colors[i * boardSize + j] = new Color(
                            (start == Color.Red) ? Color.White : Color.Red, 255);
                }
            }
            start = new Color((start == Color.Red) ? Color.White : Color.Red, 255);
        }
        board.Colors = colors;

        board.Location = new Vertex(0, 0, 5);
        board.Rotation = new Vertex(-30, 0, 0);
        board.Scale = new Vertex(1, 1, 1);
    }

    public int Run()
    {
        while (true)
        {
            Update();
            window.Wait(2);
        }
    }

    public void Update()
    {
    }

    public void Render(GraphicsUnit graphics)
    {
        graphics.FillRectangle(Color.Black, new Point(0, 0), window.Size);
        board.Render(graphics);
    }
}
