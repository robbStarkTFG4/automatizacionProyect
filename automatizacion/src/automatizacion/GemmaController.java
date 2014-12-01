/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatizacion;

import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Stop;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import pistonccontrol.PistonControl;
import pkg3deffect.Griper;

/**
 * FXML Controller class
 *
 * @author NORE
 */
public class GemmaController implements Initializable {

    // LAYOUT RELATED
    @FXML
    private TextField cantidad;
    @FXML
    private Button btn1;
    @FXML
    private SplitPane parentPane;
    @FXML
    private AnchorPane anime;

    @FXML
    private Button pruebaCom;
    @FXML
    private Button pbCom2;
    @FXML
    private Button pbCom3;
    @FXML
    private Button pbCom4;

    private Automatizacion application;

    // ANIMATION RELATED
    private static int yPoint = 200;
    private static int xPoint = 50;
    private static double pistonWidth = 70;
    private static final double cajitasHeight = 25.0;
    private static double cajitasWidth = 25.0;
    private static double containerHeight = 5.5 * (cajitasHeight) + cajitasHeight * .5;
    private static double containerWidth = cajitasWidth * 1.4;
    private static double boxContainerX = xPoint + 70 + 5;
    private static final double boxContainerY = yPoint - containerHeight + cajitasHeight;
    private static double piston2X = boxContainerX + containerWidth * 1.20;
    private static double piston2Y = boxContainerY;
    private static double structureY = yPoint - (3 * containerHeight / 4) + cajitasHeight;
    private static double piston3Y = yPoint - (5 * containerHeight / 8);

    private int cajaColor = 0; // probable variable que va ir en singleton
    private static final int CAJA_VERDE = 21312;//IGUAL
    private static final int CAJA_NEGRA = 2312;//igual
    private static final int CAJA_AZUL = 8712;//igual

    private List<Rectangle> cajitas;
    private List<Rectangle> cajitas2;
    private Rectangle current;
    private int currentIndex = 0;
    private double cajitasYPoint = 0;
    private int currentPiston = 0;
    public PistonControl piston;
    public PistonControl piston2;
    public PistonControl piston3;
    private ComboBox comboBox;
    private ObservableList<Rectangle> boxesPhase1;
    private Group root;
    private ObservableList<Rectangle> boxesPhase2;
    public PistonControl piston4;
    public PistonControl piston5;
    public PistonControl piston6;
    private double piston5Home = 0;
    private Griper grip;
    private double caidaCajas = 3000;
    private Rectangle structure;
    private boolean griperBound = false;
    private final Color colorStrokeOfPaths = Color.web("#2F4F4F");
    private final Color vastagoColor = Color.web("#2F4F4F");
    private Rectangle currentBoxGrip;
    private StackPane stackCajaVerde;
    private Text cajaGreens;
    private StackPane stackCajaBlack;
    private Text cajaBlacks;
    private StackPane stackCajaBlue;
    private Text cajaBlues;

    private int contadorCajaVerde = 0;
    private int contadorCajaNegra = 0;
    private int contadorCajaAzul = 0;
    private ComboBox comboColor;
    private SimpleDoubleProperty vastagoRetraction;
    private final static int PISTON_SPEED = 6;
    public boolean turnOnScada = true;

    // SINGLETON
    private Crixus instance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // botoness
        pruebaCom.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Task<Void> write = new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        System.out.println("hola tuuuuu");
                        Crixus.getInstance().getCommand().setStart(true);
                        return null;
                    }

                };
                new Thread(write).start();
            }
        });
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("dinamicamente");
                try {
                    AnchorPane pane = (AnchorPane) FXMLLoader.load(GemmaController.class.getResource("prueba.fxml"));
                    parentPane.getItems().set(1, pane);
                    for (Node cajita : pane.getChildren()) {

                    }
                } catch (IOException ex) {
                    Logger.getLogger(GemmaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        pbCom2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Crixus.getInstance().getCommand().stop.set(true);
            }
        });

        pbCom3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Crixus.getInstance().getCommand().anotherStask.set(true);
                //  Crixus.getInstance().getRead().readRegisters();
            }
        });

        pbCom4.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Crixus.getInstance().getCommand().contador.set(Integer.parseInt(cantidad.getText()));
            }
        });

        // FIN BOTONES
        // TODO
        vastagoRetraction = new SimpleDoubleProperty(PistonControl.POS_1);
        vastagoRetraction.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //   System.out.println("modifique la retraccion del vastago");
                try {
                    piston4.setVastagoRetractPosition((int) (Double.parseDouble(newValue.toString())));
                } catch (Exception ex) {
                    Logger.getLogger(GemmaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        instance = Crixus.getInstance();
        instance.setGemma(this);
        anime.getChildren().add(animacion());
        anime.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

      /*  Thread animStates = new Thread() {

            @Override
            public void run() {
                while (turnOnScada) {
                    Crixus.getInstance().getRead().readRegisters();
                }
            }

        };

        animStates.start();*/
        Task<Void> tk=new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (turnOnScada) {
                    Crixus.getInstance().getRead().readRegisters();
                }
                return null;
            }
        };
        
        new Thread(tk).start();
        
        
    }

    public void setApp(Automatizacion application) {
        this.application = application;
    }

    private Group animacion() {

        ObservableList<Integer> options
                = FXCollections.observableArrayList(
                        0,
                        1,
                        2,
                        3,
                        4,
                        5
                );
        comboBox = new ComboBox(options);

        ObservableList<String> options2
                = FXCollections.observableArrayList(
                        "verde",
                        "negro",
                        "azul"
                );
        comboColor = new ComboBox(options2);
        comboColor.valueProperty().addListener(new ChangeListener() {

            @Override

            public void changed(ObservableValue ov, Object t, Object t1) {
                System.out.println(t1.toString());
                switch (t1.toString()) {
                    case "verde":
                        cajaColor = CAJA_VERDE;
                        try {
                            vastagoRetraction.set(PistonControl.POS_3);

                        } catch (Exception ex) {
                            Logger.getLogger(GemmaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "negro":
                        cajaColor = CAJA_NEGRA;
                        try {
                            vastagoRetraction.set(PistonControl.POS_2);

                        } catch (Exception ex) {
                            Logger.getLogger(GemmaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "azul":
                        cajaColor = CAJA_AZUL;
                        try {
                            vastagoRetraction.set(PistonControl.POS_1);

                        } catch (Exception ex) {
                            Logger.getLogger(GemmaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        });

        HBox box = new HBox();
        box.setLayoutX(40);
        box.setLayoutY(0);
        box.setSpacing(10);

        Button btn = new Button("Retraer");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                PistonControl temp = null;
                Integer val = (Integer) comboBox.getValue();

                if (val == null) {
                    return;
                }

                switch (val) {
                    case 0:
                        temp = piston;
                        break;
                    case 1:
                        temp = piston2;
                        break;
                    case 2:
                        temp = piston3;
                        break;
                    case 3:
                        temp = piston4;
                        break;
                    case 4:
                        temp = piston5;
                        break;
                    case 5:
                        temp = piston6;
                        break;
                    default:
                        break;
                }
                if (temp != null) {
                    temp.setVastagoState(false);
                }
            }
        });

        Button btn2 = new Button("Extender");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                PistonControl temp = null;
                Integer val = (Integer) comboBox.getValue();

                if (val == null) {
                    return;
                }

                switch (val) {
                    case 0:
                        temp = piston;
                        break;
                    case 1:
                        temp = piston2;
                        break;
                    case 2:
                        temp = piston3;
                        break;
                    case 3:
                        temp = piston4;
                        break;
                    case 4:
                        temp = piston5;
                        break;
                    case 5:
                        temp = piston6;
                        griperBound = false;
                        break;
                    default:
                        break;
                }
                if (temp != null) {
                    temp.setVastagoState(true);
                }
            }
        });

        // CAJAS CONTENEDORAS
        stackCajaVerde = new StackPane();
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKGREEN);
        redMaterial.setSpecularColor(Color.GREEN);

        Cylinder myCylinder = new Cylinder(25, 50);

        //myCylinder.setTranslateX(xPoint * 3);
        // myCylinder.setTranslateY(yPoint * 1.5);
        myCylinder.setMaterial(redMaterial);
        stackCajaVerde.setTranslateX(xPoint * 2.5);
        stackCajaVerde.setTranslateY(yPoint * 1.5);

        cajaGreens = new Text("0");
        cajaGreens.setFill(Color.WHITE);
        cajaGreens.setFont(new javafx.scene.text.Font(Font.SANS_SERIF, 25));

        stackCajaVerde.getChildren().addAll(myCylinder, cajaGreens);

        stackCajaBlack = new StackPane();
        final PhongMaterial blackMat = new PhongMaterial();
        blackMat.setDiffuseColor(Color.BLACK);
        blackMat.setSpecularColor(Color.WHITE);

        Cylinder myCylinder2 = new Cylinder(25, 50);
        myCylinder2.setMaterial(blackMat);

        stackCajaBlack.setTranslateX(stackCajaVerde.getTranslateX() * 2.5);
        stackCajaBlack.setTranslateY(yPoint * 1.5);

        cajaBlacks = new Text("0");
        cajaBlacks.setFill(Color.YELLOW);
        cajaBlacks.setFont(new javafx.scene.text.Font(Font.SANS_SERIF, 25));

        stackCajaBlack.getChildren().addAll(myCylinder2, cajaBlacks);

        stackCajaBlue = new StackPane();
        final PhongMaterial bluekMat = new PhongMaterial();
        bluekMat.setDiffuseColor(Color.BLUE);
        bluekMat.setSpecularColor(Color.WHITE);

        Cylinder myCylinder3 = new Cylinder(25, 50);
        myCylinder3.setMaterial(bluekMat);

        stackCajaBlue.setTranslateX(stackCajaBlack.getTranslateX() * 1.5);
        stackCajaBlue.setTranslateY(yPoint * 1.5);

        cajaBlues = new Text("0");
        cajaBlues.setFill(Color.WHITE);
        cajaBlues.setFont(new javafx.scene.text.Font(Font.SANS_SERIF, 25));

        stackCajaBlue.getChildren().addAll(myCylinder3, cajaBlues);

        //myCylinder.s
        // FINAL DE AGREGADA DE CAJAS
        // BEGINNING OF STAGE 1
        DropShadow p1 = new DropShadow();
        // p1.setRadius(5);
        piston = new PistonControl();
        piston.setPistonWidth(cajitasHeight - 5);
        piston.setPistonHeight(pistonWidth);
        // piston.setPistonBodyColor(Color.CHOCOLATE);
        piston.setEffect(p1);
        piston.setPistonVastgoLength(40.0);
        piston.setPistonSpeed(PISTON_SPEED);
        piston.setVastagoColor(vastagoColor);
        piston.setLayoutX(xPoint);
        piston.setLayoutY(yPoint);
        piston.setPistonOrientation(PistonControl.horizontal);
        piston.getVastagoStateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t && !t1) {
                    //.out.println("muevelo");
                    double[] yPoints = new double[currentIndex + 1];

                    for (int i = currentIndex; i >= 0; i--) {
                        //     System.out.println(cajitas.get(i).getLayoutY());

                        yPoints[i] = boxesPhase1.get(i).getTranslateY();
                        //  System.out.println(yPoints[i]);
                    }

                    for (int i = currentIndex - 1; i >= 0; i--) {
                        // System.out.println(i);
                        TranslateTransition tt = new TranslateTransition(Duration.millis(caidaCajas), boxesPhase1.get(i));
                        tt.setByY(yPoints[i + 1] - boxesPhase1.get(i).getTranslateY());
                        //    tt.setCycleCount((int) 4f);
                        tt.setAutoReverse(true);
                        tt.setInterpolator(Interpolator.EASE_BOTH);
                        //translate.cycleCount(Timeline.INDEFINITE);
                        // translate.interpolator(Interpolator.EASE_BOTH);
                        // cajitas.get(cajitas.size() - 1).getLocalToParentTransform().

                        if (i == 0) {
                            tt.setOnFinished(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent t) {
                                    Rectangle rec = new Rectangle();
                                    rec.setTranslateX(xPoint + pistonWidth + 10);
                                    rec.setTranslateY(boxContainerY + 1 * cajitasHeight - cajitasHeight + 1 * 3);
                                    rec.setWidth(cajitasWidth);
                                    rec.setHeight(cajitasHeight);
                                    rec.setFill(Color.BROWN);
                                    rec.setOpacity(0.1);
                                    rec.setBlendMode(BlendMode.ADD);
                                    //      System.out.println("Primera Caja en el Contenedor: " + rec.getLayoutY());
                                    boxesPhase1.add(0, rec);
                                    root.getChildren().add(rec);
                                    checkCollition(rec, piston, yPoint);
                                    checkCollition3(rec, piston3, yPoint);
                                    checkCollitionMarker(rec);
                                    FadeTransition ft = new FadeTransition(Duration.millis(2000), rec);
                                    ft.setFromValue(0.1);
                                    ft.setToValue(1.0);
                                    // ft.setCycleCount(4);
                                    ft.setAutoReverse(true);
                                    ft.play();

                                }
                            });
                        }
                        tt.play();
                    }
                }
            }

        });
        DropShadow p2 = new DropShadow();
        // p1.setRadius(5);
        piston2 = new PistonControl();
        piston2.setPistonWidth(cajitasHeight);
        piston2.setPistonHeight(pistonWidth);

        // piston.setPistonBodyColor(Color.CHOCOLATE);
        piston2.setEffect(p2);
        piston2.setPistonVastgoLength(47.0);
        piston2.setPistonSpeed(PISTON_SPEED);
        piston2.setLayoutX(piston2X);
        piston2.setLayoutY(piston2Y);
        piston2.setPistonOrientation(PistonControl.vertical);
        piston2.setPlate(true);
        piston2.setVastagoColor(vastagoColor);
        piston2.getVastagoStateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

            }

        });

        DropShadow p3 = new DropShadow();
        p3.setRadius(0.5);
        piston3 = new PistonControl();
        piston3.setEffect(p3);
        piston3.setPistonWidth(cajitasHeight);
        piston3.setPistonHeight(pistonWidth);
        // piston.setPistonBodyColor(Color.CHOCOLATE);
        piston3.setPistonVastgoLength(80.0);
        piston3.setPistonSpeed(PISTON_SPEED);
        piston3.setLayoutX(piston2X * 1.6);
        piston3.setLayoutY(piston3Y);
        piston3.setVastagoColor(vastagoColor);
        piston3.setPistonOrientation(PistonControl.vertical);
        piston3.getVastagoStateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t && !t1) {
                    System.out.println("ya acabe");
                    if (current != null) {
                        RotateTransition rotate = new RotateTransition(Duration.seconds(1.5), current);
                        rotate.setFromAngle(0);
                        rotate.setToAngle(720);
                        rotate.play();
                        rotate.setOnFinished(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent t) {
                                current.blendModeProperty().set(BlendMode.ADD);
                                boxesPhase1.get(boxesPhase1.indexOf(current)).setTranslateX(0);
                                boxesPhase1.get(boxesPhase1.indexOf(current)).setTranslateY(0);
                                // observableList.get(observableList.size() - 1).setVisible(false);
                                root.getChildren().remove(boxesPhase1.get(boxesPhase1.indexOf(current)));
                                boxesPhase1.remove(boxesPhase1.get(boxesPhase1.indexOf(current)));

                                Rectangle rec = new Rectangle();
                                rec.setTranslateX(boxContainerX * 3 - 1.3 * cajitasWidth / 4);
                                rec.setTranslateY(yPoint);
                                rec.setWidth(cajitasWidth);
                                rec.setHeight(cajitasHeight);
                                rec.setFill(Color.BROWN);
                                boxesPhase2.add(rec);
                                root.getChildren().add(rec);
                                gripBox(rec);
                            }
                        });
                    }
                }

            }

        });
        Rotate rotationTransform = new Rotate(40, 0, 0); // half of layout coordinates
        piston3.getTransforms().add(rotationTransform);

        cajitas = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {

            Rectangle rec = new Rectangle();
            rec.setTranslateX(xPoint + pistonWidth + 10);

            rec.setTranslateY(boxContainerY + i * cajitasHeight - cajitasHeight + i * 3);
            rec.setWidth(cajitasWidth);
            rec.setHeight(cajitasHeight);
            rec.setFill(Color.BROWN);
            rec.setBlendMode(BlendMode.ADD);
            // System.out.println(boxContainerY + i * cajitasHeight + 2);

            cajitas.add(rec);
            checkCollition(rec, piston, i - 1);
            checkCollition3(rec, piston3, i - 1);
            checkCollitionMarker(rec);
            if (i == 5) {
                cajitasYPoint = boxContainerY + i * cajitasHeight - cajitasHeight + i * 3;
            }
        }

        boxesPhase1 = FXCollections.observableList(cajitas);
        boxesPhase1.addListener(new ListChangeListener<Rectangle>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends Rectangle> change) {
                // System.out.println("LA LISTA SUFRIO UN CAMBIO");

            }

        });

        Rectangle boxContainer = new Rectangle();
        boxContainer.setWidth(containerWidth);
        boxContainer.setHeight(containerHeight);
        boxContainer.setLayoutX(boxContainerX);
        boxContainer.setLayoutY(boxContainerY);
        Stop[] stops = new Stop[]{new Stop(0, Color.GRAY), new Stop(1, Color.rgb(224, 223, 219))};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        boxContainer.setFill(gradient);
        //boxContainer.setOpacity(.5);
        DropShadow shadow = new DropShadow();
        boxContainer.setEffect(shadow);

        // END OF STAGE 1
        // BEGINNING OF STAGE 2
        cajitas2 = new ArrayList<>();

        boxesPhase2 = FXCollections.observableList(cajitas2);
        boxesPhase2.addListener(new ListChangeListener<Rectangle>() {

            @Override
            public void onChanged(ListChangeListener.Change<? extends Rectangle> change) {
                //   System.out.println("LA LISTA SUFRIO UN CAMBIO CAJITAS2");

            }

        });

        DropShadow strucShaw = new DropShadow();
        structure = new Rectangle();
        structure.setTranslateX(boxContainerX * 4);
        structure.setTranslateY(structureY);
        structure.setWidth(3 * containerWidth / 4);
        structure.setHeight(3 * containerHeight / 4);
        Stop[] structureStops = new Stop[]{new Stop(0, Color.GRAY), new Stop(1, Color.rgb(224, 223, 219))};
        LinearGradient gradientStruct = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        structure.setFill(gradientStruct);
        structure.setEffect(strucShaw);

        DropShadow p4 = new DropShadow();
        piston4 = new PistonControl();
        piston4.setEffect(p4);
        piston4.setPistonWidth(3 * cajitasHeight / 4);
        piston4.setPistonHeight(3 * pistonWidth / 4);
        piston4.setInitialVastagoLength(15.0);
        piston4.setPistonVastgoLength(75.0);
        piston4.setPistonSpeed(PISTON_SPEED);
        piston4.setTranslateX(structure.getTranslateX() + 1.4 * structure.getWidth());
        piston4.setTranslateY(structure.getTranslateY());
        piston4.setPistonOrientation(PistonControl.vertical);
        piston4.setVastagoColor(vastagoColor);
        piston4.getVastagoStateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (t && !t1) {

                }
            }

        });
        Rotate rotationTransformForPiston4 = new Rotate(90, 0, 0); // half of layout coordinates
        piston4.getTransforms().add(rotationTransformForPiston4);

        DropShadow p5 = new DropShadow();
        p5.setRadius(1);
        piston5 = new PistonControl();
        piston5.setEffect(p5);
        piston5.setPistonWidth(cajitasHeight / 2);
        piston5.setPistonHeight(pistonWidth / 2);
        piston5.setTranslateX(structure.getTranslateX() - 1 * structure.getWidth());
        piston5Home = piston5.getTranslateX();
        piston5.setTranslateY(structure.getTranslateY() * 0.95);
        piston5.setPistonOrientation(PistonControl.vertical);
        piston5.setInitialVastagoLength(20.0);
        piston5.setPistonVastgoLength(60.0);
        piston5.setPistonSpeed(PISTON_SPEED);
        piston5.setVastagoColor(vastagoColor);
        checkCollitionWithPiston(piston5, piston4); // corredera

        DropShadow p6 = new DropShadow();
        p6.setRadius(2);
        piston6 = new PistonControl();
        piston6.setEffect(p6);
        piston6.setPistonWidth(cajitasHeight / 2);
        piston6.setPistonHeight(pistonWidth / 2);
        piston6.setTranslateX(structure.getTranslateX());
        piston6.setTranslateY(piston5.getBoundsInParent().getMaxY());
        piston6.setPistonOrientation(PistonControl.vertical);
        piston6.setInitialVastagoLength(5.0);
        piston6.setPistonVastgoLength(11.0);
        piston6.setPistonSpeed(PISTON_SPEED);
        piston6.setVastagoColor(vastagoColor);
        piston6.setPlate(Boolean.TRUE);
        //piston6.setPistonVastgoLength(60.0);
        Rotate turn = new Rotate(90, 0, 0);
        piston6.getTransforms().add(turn);
        piston6.getVastagoStateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {

                if (t && !t1 && griperBound) {
                    System.out.println("guarda la caja");

                    double yPos = 0;
                    double xPos = 0;
                    CubicCurveTo curve = null;
                    switch (cajaColor) {
                        case CAJA_VERDE:

                            yPos = stackCajaVerde.getTranslateY();
                            xPos = stackCajaVerde.getTranslateX();
                            curve = new CubicCurveTo(currentBoxGrip.getTranslateX(), currentBoxGrip.getTranslateY(),
                                    xPos / 4, 2 * yPos, xPos, yPos);

                            break;
                        case CAJA_NEGRA:

                            yPos = stackCajaBlack.getTranslateY();
                            xPos = stackCajaBlack.getTranslateX();
                            curve = new CubicCurveTo(currentBoxGrip.getTranslateX(), currentBoxGrip.getTranslateY(),
                                    3 * xPos / 4, yPos, xPos, yPos);

                            break;

                        case CAJA_AZUL:

                            yPos = stackCajaBlue.getTranslateY();
                            xPos = stackCajaBlue.getTranslateX();
                            curve = new CubicCurveTo(currentBoxGrip.getTranslateX(), currentBoxGrip.getTranslateY(),
                                    xPos / 4, 2 * yPos, xPos, yPos);

                            break;
                        default:
                            break;
                    }

                    Path path = new Path();
                    path.getElements().addAll(
                            new MoveTo(currentBoxGrip.getTranslateX(), currentBoxGrip.getTranslateY()),
                            curve
                    // new CubicCurveTo(0, 120, 0, 240, xPos, yPos)
                    );

                    path.setStroke(colorStrokeOfPaths);
                    path.setOpacity(0);
                    //path.getStrokeDashArray().setAll(5d,5d);
                    root.getChildren().add(path);

                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(2));
                    pathTransition.setPath(path);
                    pathTransition.setNode(currentBoxGrip);
                    pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pathTransition.play();
                    pathTransition.setOnFinished(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent t) {
                            System.out.println("ya llegue a mi contenedor");
                            switch (cajaColor) {
                                case CAJA_VERDE:
                                    contadorCajaVerde++;
                                    cajaGreens.setText(String.valueOf(contadorCajaVerde));
                                    break;
                                case CAJA_NEGRA:
                                    contadorCajaNegra++;
                                    cajaBlacks.setText(String.valueOf(contadorCajaNegra));
                                    break;
                                case CAJA_AZUL:
                                    contadorCajaAzul++;
                                    cajaBlues.setText(String.valueOf(contadorCajaAzul));
                                    break;
                                default:
                                    contadorCajaVerde++;
                                    cajaGreens.setText(String.valueOf(contadorCajaVerde));
                                    break;
                            }
                            // cajaColor = 0;
                            boxesPhase2.remove(currentBoxGrip);
                            root.getChildren().remove(currentBoxGrip);
                            currentBoxGrip = null;
                            pathTransition.stop();

                        }
                    });
                }
            }

        });

        checkGriperMovement(piston6);

        grip = new Griper();
        grip.setTranslateX(piston6.getTranslateX() - 1.5 * piston4.getPistonHeight());
        grip.setGriperHeight(25.0);
        grip.setGriperWidth(70.0);
        grip.setTranslateY(piston6.getTranslateY() * 1.35);

        checkGriperMovement2(grip);

        // END OF STAGE 2
        // ADD ALL NODES
        box.getChildren().setAll(btn, btn2, comboBox, comboColor);

        root = new Group();
        root.getChildren().addAll(boxContainer, box, piston, piston2, piston3, structure, piston4, piston5, piston6, grip, stackCajaVerde,
                stackCajaBlack, stackCajaBlue);

        for (Rectangle cajita : boxesPhase1) {
            root.getChildren().add(cajita);
        }

        return root;
    }

    // piston que saca las cajas del contenedor
    private void checkCollition(Rectangle rec, PistonControl piston, int index) {
        piston.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                if (rec.getBoundsInParent().intersects(t1)) {
                    currentIndex = cajitas.indexOf(rec);
                    rec.setTranslateX(t1.getMaxX());
                    rec.setBlendMode(null);
                }
            }
        });
    }

    // piston que etiqueta las cajas
    private void checkCollitionMarker(Rectangle rec) {
        piston2.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                if (rec.getBoundsInParent().intersects(t1)) {
                    System.out.println("ETIQUETA LA CAJAAAA!!!!");
                }
            }
        });
    }

    // piston who will move boxes to secondPhase, thus eliminating the objecto in cajitas.
    private void checkCollition3(Rectangle rec, PistonControl piston, int index) {
        piston3.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                if (rec.getBoundsInParent().intersects(t1)) {
                    current = rec;
                    rec.setTranslateY(t1.getMaxY());
                    rec.setTranslateX(rec.getTranslateX() - .75);
                }
            }

        });

    }

    //metodo para  el piston que mueve la corredera horizontal en la fase 2;
    private void checkCollitionWithPiston(PistonControl rec, PistonControl piston) {
        piston.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                //  System.out.println(t1);
                //if (rec.getBoundsInParent().intersects(t1)) {
                rec.setTranslateX(t1.getMinX());

                // }
            }
        });
    }

    //metodo para  mover verticalmente el griper
    private void checkGriperMovement(PistonControl rec) {
        piston5.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                rec.setTranslateY(t1.getMaxY());
                double width = rec.getPistonHeight();
                width = t1.getMinX() + 3 * width / 4;
                rec.setTranslateX(width);

            }
        });
    }

    //metodo que mueve la parte estatica del griper.
    private void checkGriperMovement2(Griper grip) {
        piston5.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                double height = grip.getGriperHeight();
                double width = grip.getGriperWitdh();
                grip.setTranslateY(t1.getMaxY() - 3 * height / 4);

                grip.setTranslateX(t1.getMinX() - 3 * width / 4);

            }
        });
    }

    // method that checks when the piston griper is grabbing the box.
    private void gripBox(Rectangle rec) {
        piston6.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                if (rec.getBoundsInParent().intersects(t1)) {
                    //   System.out.println(t1.getMinX());
                    currentBoxGrip = rec;
                    rec.setTranslateX(t1.getMinX() - cajitasWidth);
                    rec.setTranslateY(t1.getMinY());
                    griperBound = true;
                }

                if (griperBound) {
                    rec.setTranslateX(t1.getMinX() - cajitasWidth);
                }
            }
        });

    }
}
