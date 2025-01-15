/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

public interface DAOInterface<T, ID> {
    public int insert(T t);
    public int update(T t);
    public int delete(T t);
    public List<T> selectAll();
    public T selectById(ID id);
}
