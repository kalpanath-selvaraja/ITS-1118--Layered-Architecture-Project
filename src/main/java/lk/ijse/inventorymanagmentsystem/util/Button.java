///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package lk.ijse.inventorymanagmentsystem.util;
//
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;
//
///**
// *
// * @author kalpanath
// */
//public class Button {
//    
//    private static javafx.scene.control.Button currentlySelectedButton = null;
//    private static CustomerDTO selectedCustomer = null;
//    
//    
//    public void actionButton(TableColumn btn , String DTO ){
//        btn.setCellFactory(param -> new TableCell<>() {
//                private final javafx.scene.control.Button action = new javafx.scene.control.Button ("Select");
//                {
//                    
//                    
//                    action.setStyle("""
//                        -fx-background-color: #3b82f6;
//                        -fx-text-fill: white;
//                        -fx-background-radius: 6;
//                    """);
//                    
//                    
//                    action.setOnAction(event ->{
//                        
//                        
//                        DTO dto = getTableView().getItems().get(getIndex());
//                            
//                        if(action ==  currentlySelectedButton){
//                            action.setText("Select");
//                            currentlySelectedButton = null;
//                            selectedCustomer = null;
//                        } else {
//
//                            if (currentlySelectedButton != null) {
//                                currentlySelectedButton.setText("Select");
//                            }
//
//
//                            action.setText("Selected");
//                            currentlySelectedButton = action;
//                            selectedCustomer = dto;
//                            
//                            dto.setCusDTO(selectedCustomer);
//
//                            System.out.println("Selected: " + dto.getName());
//                            
//                            
//                        }   
//                            
//                            
//                    });
//                }
//        
//                
//                @Override
//                protected void updateItem(Void item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty) {
//                        setGraphic(null);
//                    } else {
//                        setGraphic(action);
//                    }
//                }
//        });
//    }
//}
