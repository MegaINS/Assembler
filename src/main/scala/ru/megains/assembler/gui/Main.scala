package ru.megains.assembler.gui

import java.awt.{Color, Component, GraphicsEnvironment}
import javax.swing.{DefaultListCellRenderer, JComboBox, JLabel, JList, JOptionPane, SwingUtilities}
import javax.swing.text.{SimpleAttributeSet, Style, StyleConstants, StyledDocument, TabSet, TabStop}
import scala.swing.{Action, ButtonGroup, CheckMenuItem,  Dimension, Font, Frame, MainFrame, Menu, MenuBar, MenuItem, RadioMenuItem, Separator, SimpleSwingApplication, TextPane}


object Main extends SimpleSwingApplication {

    val textCode = "\nSTART:\n\tMOV 9 $1\n\tMOV 100 $2\n\n\tPUSH $1\n\tPUSH $2\n\n\tCALL FILL_ARRAY\n\n\tMOV 0 $3\n\n.LOOP1:\n\tLW $2 $4\n\tPUSH $4\n\tCALL CHECK_EVEN\n\tPUSH $4\n\tJE .EVEN\n\tCALL OP_NOT_EVEN\n\tJMP .LABLE1\n.EVEN:\n\tCALL OP_EVEN\n.LABLE1:\n\n\tSW $12 $2\n\tCMP $3 $1\n\tJE END\n\tINC\t$2\n\tINC\t$3\n\tJMP .LOOP1\n\nFILL_ARRAY:\n\n    LW [SP+2] $11\n    LW [SP+4] $10\n\t\n\tMOV 0 $12\n\n.FILL_LOOP:\n\tSW $12 $11\n\tCMP $12 $10\n\tJE .STOP\n\tINC $11\n\tINC $12\n\tJMP .FILL_LOOP\n.STOP:\n\tRET 4\n\t\nCHECK_EVEN:\n\n    LW [SP+2] $10\n\tAND $10 1\n\tCMP $10 0\n\tRET 2\n\nOP_EVEN:\n\n\tLW [SP+2] $10\n\tMOV 3 $11\n\tPUSH $10\n\tPUSH $11\n\n\tCALL MULTIPLY\n\n\tADD $12 7\n\n\tRET 2\n\t\nOP_NOT_EVEN:\n\n    LW [SP+2] $10\n\n\tPUSH $10\n\tPUSH $10\n\tCALL MULTIPLY\n\n\tSUB $12 3\n\n\tRET 2\n\nMULTIPLY:\n\n    LW [SP+2] $9\n    LW [SP+4] $10\n\tMOV 0 $11\n\tMOV 0 $12\n.LOOP:\n\tCMP $10 $11\n\tJE .END\n\tADD $12 $9 $12\n\tINC $11\n\tJMP .LOOP\n.END:\n\tRET 4\n\nEND:\n\tSHUTDOWN \n\n"


    def top: Frame = new MainFrame {
        title = "Scala Swing Demo"

        /*
         * Create a menu bar with a couple of menus and menu items and
         * set the result as this frame's menu bar.
//         */
//        menuBar = new MenuBar {
//            contents += new Menu("A Menu") {
//                contents += new MenuItem("An item")
//                contents += new MenuItem(Action("An action item") {
//                    println("Action '" + title + "' invoked")
//                })
//                contents += new Separator
//                contents += new CheckMenuItem("Check me")
//                contents += new CheckMenuItem("Me too!")
//                contents += new Separator
//                val a = new RadioMenuItem("a")
//                val b = new RadioMenuItem("b")
//                val c = new RadioMenuItem("c")
//                val mutex = new ButtonGroup(a, b, c)
//                contents ++= mutex.buttons
//            }
//            contents += new Menu("Empty Menu")
//        }


        contents = new TextPane() {
            text = textCode
            font = Font("Consolas",Font.Plain,20)

            val blue = new SimpleAttributeSet()
            StyleConstants.setForeground(blue, Color.blue)
            StyleConstants.setBackground(blue, Color.RED)
            //  StyleConstants.setTabSet(blue,TabSet(Array(TabStop(10))))

            val doc: StyledDocument = styledDocument
            val default: Style = doc.getStyle("default")
            StyleConstants.setTabSet(default,new TabSet(Array(new TabStop(20, 0, 5))))
           // StyleConstants.setFontSize(default, 12)
          //  StyleConstants.setFontFamily(default,Font.Plain )

            doc.setCharacterAttributes(8, 2, blue, false)


        }
        size = new Dimension(800, 600)






    }
}


object ShowFonts {
    def main(args: Array[String]): Unit = {
        SwingUtilities.invokeLater(() => {
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment
            val fonts = ge.getAvailableFontFamilyNames
            val fontChooser = new JComboBox(fonts)
            fontChooser.setRenderer(new FontCellRenderer())
            JOptionPane.showMessageDialog(null, fontChooser)

        })
    }
}

class FontCellRenderer extends DefaultListCellRenderer  {
   override def getListCellRendererComponent(list: JList[_],
                                     value: AnyRef,
                                     index: Int,
                                     isSelected: Boolean,
                                     cellHasFocus: Boolean): Component = {
        val label:JLabel  = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus).asInstanceOf
        val font = Font(value.toString, Font.Plain, 20)
        label.setFont(font)
        label
    }
}