/**
 *
 * @author Dylan Heid
 */
public class SortedList<T extends Comparable<? super T>> extends LList<T> {
    
   @Override
   public boolean add(T o) {
      Node n = new Node(o);
      if(size == 0){
         first = n;
         last = n;
      }else{
         int index = indexOf(o);
         if(index < 0)
            index = -(index + 1);
         if(index == size){
            link(last, n);
            last = n;
         }else{
            Node next = getNodeAt(index);
            insertBefore(n, next);
            if(index == 0){
               first = n;
            }
         }//ends else
      }//ends else
      size++;
      return true;
   }//ends boolean add(T o)
    
   /**
    * This method should just return false or throw an exception.
    * Do not allow the user of this method to insert a value into a specific
    * location.
    * @param index
    * @param o
    * @return false
    */
   @Override
   public boolean add(int index, T o) {
      //Nothing to do here!
      return false;
   }//ends boolean add(int index, T o)
    
    
   /**
    * This method should just return null or throw an exception.
    * @param index
    * @param o
    * @return null
    */
   @Override
   public T set(int index, T o) {
      return null;
   }//ends T set(int index, T o)
    
   @Override
   public int indexOf(T o) {
      //TODO: This method should return the index of the first instance
      //      of the item, if the item is already in the collection.
      //      IF IT IS NOT IN THE COLLECTION, IT SHOULD RETURN A VALUE
      //      BETWEEN -1 AND -(size+1), INDICATING WHERE IT MIGHT BE INSERTED
      //      IN THE COLLECTION.
      Node current = first;
      int index;
      for(index = 0; index < size; index++){
         if(current.getElement().equals(o))
            return index;
         else if(current.getElement().compareTo(o) > 0)
            break;
         else
            current = current.getNext();
      }//ends for search
      return -(index + 1);
   }//ends indexOf(T o)
    
   @Override
   public int lastIndexOf(T o) {
      //TODO: This method should return the index of the last instance
      //      of the item, if the item is already in the collection.
      //      IF IT IS NOT IN THE COLLECTION, IT SHOULD RETURN A VALUE
      //      BETWEEN -1 AND -(size+1), INDICATING WHERE IT MIGHT BE INSERTED
      //      IN THE COLLECTION.
      Node current = last;
      int index;
      for(index = size - 1; index > -1; index--){
         if(current.getElement().equals(o))
            return index;
         else if(current.getElement().compareTo(o) < 0)
            break;
         else
            current = current.getPrevious();
      }//ends for search
      return -(index + 1);  
   }//ends int lastIndexOf(T o)
    
   @Override
   public boolean contains(T o) {
      //TODO: The old method will work, but we can do better. Write an
      //      optimized contains method.
      return super.contains(o);
   }//ends boolean contains(T o)
    
}//ends class